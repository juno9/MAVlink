package MAVLinkTCP_MISSION;


import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Parser;
import com.MAVLink.common.*;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_RESULT;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.MAVLink.common.msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG;
import static com.MAVLink.common.msg_mission_count.MAVLINK_MSG_ID_MISSION_COUNT;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_NAV_TAKEOFF;
import static com.MAVLink.enums.MAV_RESULT.MAV_RESULT_IN_PROGRESS;

public class MAVLinkTCP_Server2 {

//드론 역할을 하게될 것

    //    절차는 다음과 같습니다:
//
//    1. GCS에서 드론으로 MISSION_COUNT 메시지를 보냅니다. 이 메시지에는 업로드할 임무 항목의 수가 포함됩니다.- 완료, 텍스트 출력 확인
//
//    3. 드론은 MISSION_REQUEST_INT 메시지로 응답합니다. 이 메시지는 첫 번째 임무 항목(seq=0)을 요청합니다.
//
//    5. GCS에서 MISSION_ITEM_INT 메시지로 응답합니다. 이 메시지에는 요청된 임무 항목이 포함됩니다.
//    6. 드론과 GCS는 이 요청/응답 사이클을 반복합니다. seq 값을 증가시켜가며 모든 임무 항목이 업로드될 때까지 (seq=count-1) 이 과정을 반복합니다.
//    7. 드론은 마지막 임무 항목을 받으면 MISSION_ACK 메시지로 응답합니다. 이 메시지의 type은 MAV_MISSION_ACCEPTED이며, 임무 업로드 완료 및 성공을 의미합니다.
//    8. 드론은 업로드된 임무를 현재 임무로 설정합니다. 기존 임무 데이터는 삭제됩니다.
//    9. 드론은 임무 업로드를 완료합니다.
//    10. GCS는 MAV_MISSION_ACCEPTED를 포함하는 MISSION_ACK 메시지를 수신합니다. 이를 통해 작업 완료를 확인합니다.
//
//
//    참고 사항:
//    응답이 필요한 모든 메시지에는 대기 시간이 설정됩니다. (예: MISSION_REQUEST_INT) 대기 시간 내에 응답을 받지 못하면 요청을 다시 보내야 합니다.
//    임무 항목은 순서대로 수신되어야 합니다. 순서가 어긋난 항목이 수신되면 드론은 예상한 항목을 다시 요청해야 합니다. (순서가 틀린 항목은 삭제됩니다)
//    모든 요청에 대해 MISSION_ACK 메시지를 사용하여 오류를 신호할 수 있습니다. 이 경우 작업이 취소되고 임무가 이전 상태로 복원됩니다. 예를 들어, 드론은 임무를 업로드할 공간이 부족한 경우 MISSION_COUNT 요청에 MAV_MISSION_NO_SPACE로 응답할 수 있습니다.
//    위의 절차는 임무 항목이 MISSION_ITEM_INT 메시지로 패키징된 경우를 보여줍니다. 프로토콜 구현은 MISSION_ITEM 및 MISSION_REQUEST 메시지도 동일한 방식으로 지원해야 합니다.
//    빈 임무를 업로드하는 것(MISSION_COUNT가 0)은 임무를 지우는 것과 동일한 효과를 갖습니다.

    public static void main(String[] args) throws IOException {
        System.out.println("<< DRONE >>");
        ServerSocket serverSocket = null;
        int systemid = 1;//1번 기기라는 설정
// 포트 사용 가능 여부 판단
        try {
            serverSocket = new ServerSocket(10000);
        } catch (IOException e) {
            System.out.println("해당 포트를 열 수 없음");
            System.exit(0);
        }
//포트 사용 가능 여부 판단
        System.out.println(" - Client 접속 대기...");
        while (true) {//클라이언트 하나만 연결할거 아니니까
            try {
                Socket socket = serverSocket.accept();//연결 되면 serverSocket.accept()은 소켓 객체를 반환한다
                System.out.println(" - Client 연결 수락...");
                System.out.println("접속 Client 주소 : " + socket.getInetAddress() + " port : " + socket.getPort());

                InputStream is = socket.getInputStream();
                InputStreamReader ir = new InputStreamReader(is);
                BufferedReader bis = new BufferedReader(ir);


                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                TCPThread thread = new TCPThread(socket, bis, bw, systemid);
                thread.start();//별도의 쓰레드로 돌림
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class TCPThread extends Thread {
        private Socket socket;
        private BufferedReader dis;
        private BufferedWriter dos;
        private Parser mMavlinkParser;

        private BufferedInputStream bis;
        int systemid;

        public TCPThread(Socket recvdsocket, BufferedReader recvddis, BufferedWriter recvddos,int systemid) {
            this.socket = recvdsocket;
            this.dis = recvddis;
            this.dos = recvddos;
            this.systemid =systemid;
            mMavlinkParser = new Parser();
            try {
                bis = new BufferedInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while (true) {//이 후로 들어오는 모든 텍스트는 채팅 메시지 내용
                    System.out.println("");
                    byte[] input = new byte[2000];//1바이트 객체를 300개 선언한다
                    int bytesRead = bis.read(input);//인풋 스트림에서 읽어온 데이터를 input배열에 저장하고 bytesRead에는 읽어야 할 배열의 길이가 할당된다
                    for (int i = 0; i < bytesRead; i++) {//패킷 단위로 데이터를 쪼개준다

                        MAVLinkPacket packet = mMavlinkParser.mavlink_parse_char(input[i] & 0xff);
                        if (packet != null) {
                            MAVLinkMessage msg = packet.unpack();//

                            System.out.println("마브링크 메시지 : " + msg.toString() + "\n 패킷 : " + packet);//메시지 확인


                            switch (msg.msgid) {
                                case MAVLINK_MSG_ID_MISSION_COUNT: {
                                    msg_mission_count msg_mission_count = (msg_mission_count) msg;//받은 메시지를 msg_mission_count로 재선언


                                    if(msg_mission_count.target_system == systemid){//날아온 메시지의 타겟 시스템id와 이 메시지를 받은 시스템의 id가 같다면
                                        int count = msg_mission_count.count;//받은 메시지_미션_카운트 객체에서 미션 갯수 추출하여 count객체에 할당
                                        System.out.println("GCS 에서 날아온 아이템 갯수 : " + count);//콘솔에 GCS에서 날아온 미션 갯수 출력
                                    } else {
                                       //여기에 관제에서 드론에 보낸 메시지가 번지수를 잘못찾은 경우를 분기하려 했는데 생각해보니 그럼 연결도 안되어 있어야 맞다.

                                    }

                                    // 1. GCS에서 드론으로 MISSION_COUNT 메시지를 보냅니다. 이 메시지에는 업로드할 임무 항목의 수가 포함됩니다.------------------------------------------

                                    //드론은 MISSION_REQUEST_INT 메시지로 응답합니다. --------------------------------------
                                    msg_mission_request_int msg_mission_request_int = new msg_mission_request_int();//드론은 mission_request_int로 응답해야 하니까 새로 msg_mission_request_int객체를 만들어 줌
                                    msg_mission_request_int.seq = 0; //첫번째 미션을 요청해야 하니 메시지에 요청하는 아이텀의 순서 넣어줌
                                    msg_mission_request_int.sysid = systemid;//이 메시지를 보내는 시스템의 id를 입력
                                    msg_mission_request_int.target_system= (short) msg_mission_count.sysid;//이 메시지를 받은 시스템의 id를 입력, msg_mission_count보내온 곳의 시스템id가 들어가야 함
                                    MAVLinkPacket Mavpacket = msg_mission_request_int.pack();//미션 리퀘스트 인트 메시지를 패킷에 담음
                                    byte[] data = Mavpacket.encodePacket();//패킷을 바이트 단위 데이터로 쪼갬
                                    OutputStream os = socket.getOutputStream();//
                                    BufferedOutputStream BOS = new BufferedOutputStream(os);
                                    BOS.write(data);
                                    BOS.flush();

                                    // 드론은 MISSION_REQUEST_INT 메시지로 응답합니다. --------------------------------------
                                }


                            }

                        }
                    }
                    System.out.println("");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
