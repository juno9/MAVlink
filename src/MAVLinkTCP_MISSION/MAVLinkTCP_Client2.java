package MAVLinkTCP_MISSION;

import com.MAVLink.Arm;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Parser;
import com.MAVLink.Takeoff;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_mission_count;
import com.MAVLink.common.msg_mission_item_int;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_RESULT;

import java.io.*;
import java.net.Socket;

import static com.MAVLink.common.msg_command_ack.MAVLINK_MSG_ID_COMMAND_ACK;
import static com.MAVLink.common.msg_mission_request_int.MAVLINK_MSG_ID_MISSION_REQUEST_INT;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_NAV_TAKEOFF;
import static com.MAVLink.enums.MAV_RESULT.MAV_RESULT_IN_PROGRESS;


public class MAVLinkTCP_Client2 {

//GCS 역할을 할 것

//    절차는 다음과 같습니다:
//
//    1. GCS에서 드론으로 MISSION_COUNT 메시지를 보냅니다. 이 메시지에는 업로드할 임무 항목의 수가 포함됩니다.- 완료, 텍스트 출력 확인

//    3. 드론은 MISSION_REQUEST_INT 메시지로 응답합니다. 이 메시지는 첫 번째 임무 항목(seq=0)을 요청합니다.
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


    public static void main(String[] args) {
        String ip = "192.168.0.108";//연결할 주소
        Socket socket;
        int port = 10000;//연결할 포트
        int systemid = 0;//GCS니까 시스템 ID는 0번을 할당
        System.out.println("<<Client>>");
        try {
            socket = new Socket(ip, port);//연결할 주소와 포트를 매개변수로 소켓 객체를 생성함
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//서버에서 보내온 데이터를 읽어올 객체
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//자동으로 버퍼를 비워 flush를 사용하지 않아도 된다
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//콘솔에 입력한 데이터를 읽어줄 버퍼리더

            System.out.println("전송할 메시지를 입력하세요");

            // 클라이언트에서 입력받은 메시지를 서버로 전송, 별도의 쓰레드를 돌린다
            Thread sendMessageThread = new Thread(() -> {//
                while (true) {//메시지 한번만 보내고 말거 아니니까 반복문 계속 돌도록
                    try {


                        System.out.println("미션을 보낼 드론의 ID");
                        System.out.println("입력: ");
//--------------------------------------- 1. GCS에서 드론으로 MISSION_COUNT 메시지를 보냅니다. 이 메시지에는 업로드할 임무 항목의 수가 포함됩니다.------------------------------------------

                        short target_systemid = Short.parseShort(consoleReader.readLine());//콘솔에서 입력값 읽어온 다음 스트링에 할당
                        System.out.println("드론 ID : "+target_systemid);
                        System.out.println("업로드할 미션의 갯수 : ");
                        int item_quantitiy = Integer.parseInt(consoleReader.readLine());//콘솔에서 입력값 읽어온 다음 스트링에 할당

                        msg_mission_count msg_mission_count = new msg_mission_count();
                        msg_mission_count.sysid=systemid;//이 메시지를 보내는 시스템의 id는 고유하게 넣어줘야 함, 여기는 GCS이니까 0번을 할당
                        msg_mission_count.count = item_quantitiy; //입력 받은 숫자만큼 미션 아이템 갯수를 할당함
                        msg_mission_count.target_system=target_systemid;//이 메시지를 수신할 시스템의 id, 연결되어 있는 드론이 1번 하나 뿐일테니


                        MAVLinkPacket Mavpacket = msg_mission_count.pack();
                        byte[] data = Mavpacket.encodePacket();
                        OutputStream os = socket.getOutputStream();
                        os.write(data);
                        os.flush();
//--------------------------------------- 1. GCS에서 드론으로 MISSION_COUNT 메시지를 보냅니다. 이 메시지에는 업로드할 임무 항목의 수가 포함됩니다.------------------------------------------


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sendMessageThread.start();//쓰레드 시작

            // 서버에서 온 메시지를 출력, 별도의 쓰레드가 돌면서 메시지를 받아온다
            Thread receiveMessageThread = new Thread(() -> {
                Parser mMavlinkParser = new Parser();//파서 - 바이트단위로 들어온 데이터를 마브링크 형식에 맞는 유용한 형태로 만들어 주는 객체, 파싱한다는 것은 분석한다는 것.
                try {
                    while (true) {
                        System.out.println("");
                        byte[] input = new byte[2000];//1바이트짜리 객체를 300개 선언한다. 300인 이유는 마브링크 패킷이 최대 280바이트 이기 때문
                        //읽는 곳의 처리가 쓰는곳보다는 빨라야 한다.
                        //데이터에 따라 데이터 길이를 적절하게 맞춰줘야 한다.
                        //300은 너무 적다


//     GCS에서 MISSION_ITEM_INT 메시지로 응답합니다. 이 메시지에는 요청된 임무 항목이 포함됩니다.
//     드론과 GCS는 이 요청/응답 사이클을 반복합니다. seq 값을 증가시켜가며 모든 임무 항목이 업로드될 때까지 (seq=count-1) 이 과정을 반복합니다.
                        int bytesRead = bis.read(input);//읽어온 데이터는 input에 저장되고 bytesRead에는 배열의 길이가 저장된다


                        for (int i = 0; i < bytesRead; i++) {//배열의 길이만큼 반복하며 메시지를 파싱한다 바이트로 전달받은 데이터를 배열에 집어넣고

                            System.out.println("반복횟수 :" + (i + 1));
                            MAVLinkPacket packet = mMavlinkParser.mavlink_parse_char(input[i] & 0xff); // & : 비트 연산(논리 곱)
                            //배열에 있는 아이템을 파싱(분석)하여 패킷으로 선언한다.->바이트 데이터를 파싱을통해 유의미한 데이터로 가공한다.
                            // 한 바이트씩 받아서 프레임에 맞게 재구성하는 것이 파싱이다.(중요)
                            //언싸인드 값이 많다. 컴퓨터 구조 자바는 언싸인드가 없다. 보낼 때 언싸인드 값으로 만들기 위해, IOT나 임베디드 쪽은 언싸인드 많이 쓴다. 마이너스 값을 잘 쓰지 않기 때문


                            if (packet != null) {
                                MAVLinkMessage msg = packet.unpack();//패킷의 필드중에 페이로드 만을 추출해 내는 코드, 메시지 ID에 따라 분기처리 되어 있다.
                                System.out.println("드론으로부터 받은 마브링크 메시지 : " + msg.toString() + "\n 패킷 : " + packet);//처음 받은 마브링크 메시지
                                int targetsystem = msg.sysid;//메시지를 날려온 드론의 ID를 식별해야 함
                                switch (msg.msgid) {
                                    case MAVLINK_MSG_ID_MISSION_REQUEST_INT://만약 메세지 ID가 MISSION_REQUEST_INT 라면
                                        msg_mission_item_int msg_mission_item_int = new msg_mission_item_int();
                                        msg_mission_item_int.mission_type = 1;
                                        msg_mission_item_int.target_system = (short) targetsystem;//타겟 시스템(명령을 보낼 드론기체의 ID) 할당

                                }
                            }
                        }
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
            });
            receiveMessageThread.start();//

            sendMessageThread.join();//sendMessageThread가 종료될때 까지 메인 쓰레드를 대기시킴
            receiveMessageThread.join();//receiveMessageThread가 종료될때 까지 메인 쓰레드를 대기시킴

            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }


}
