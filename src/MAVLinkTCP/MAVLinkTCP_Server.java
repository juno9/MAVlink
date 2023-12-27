package MAVLinkTCP;


import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Parser;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.common.*;
import com.MAVLink.enums.MAV_RESULT;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.MAVLink.common.msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_NAV_TAKEOFF;
import static com.MAVLink.enums.MAV_RESULT.MAV_RESULT_IN_PROGRESS;

public class MAVLinkTCP_Server {

//드론 역할을 하게될 것
    public static void main(String[] args) throws IOException {
        System.out.println("<<Server>>");
        ServerSocket serverSocket = null;

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

                TCPThread thread = new TCPThread(socket, bis, bw);
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

        public TCPThread(Socket recvdsocket, BufferedReader recvddis, BufferedWriter recvddos) {
            this.socket = recvdsocket;
            this.dis = recvddis;
            this.dos = recvddos;
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
                    byte[] input = new byte[300];//1바이트 객체를 300개 선언한다
                    int bytesRead = bis.read(input);//인풋 스트림에서 읽어온 데이터를 input배열에 저장하고 bytesRead에는 읽어야 할 배열의 길이가 할당된다
                    for (int i = 0; i < bytesRead; i++) {//패킷 단위로 데이터를 쪼개준다
                        System.out.println("반복횟수 : " + (i+1));//메시지 확인
                        MAVLinkPacket packet = mMavlinkParser.mavlink_parse_char(input[i] & 0xff);
                        if (packet != null) {
                            MAVLinkMessage msg = packet.unpack();//패킷에 들어있는 메시지를 unpack을 통해 추출
                            System.out.println("마브링크 메시지 : " + msg.toString()+"\n 패킷 : "+packet);//메시지 확인
                            if (msg.msgid == MAVLINK_MSG_ID_COMMAND_LONG) {//메시지 ID가 MAVLINK_MSG_ID_COMMAND_LONG라면
                                System.out.println("메시지 ID : MAVLINK_MSG_ID_COMMAND_LONG");
                                msg_command_long msg_cmd = (msg_command_long) msg;//메시지를 msg_command_long형태로 재선언
                                switch (msg_cmd.command) {//클래스에 명시되어 있는 command를 기준으로 분기
                                    case MAV_CMD_NAV_TAKEOFF: {
                                        System.out.println("메시지 command : "+msg_cmd.command);
                                        socket.getOutputStream();
                                        //이제 여기서 클라이언트에 commandack메세지를 보내면 된다.
                                        msg_command_ack msg_command_ack = new msg_command_ack();
                                        msg_command_ack.command = MAV_CMD_NAV_TAKEOFF;
                                        msg_command_ack.result = MAV_RESULT.MAV_RESULT_ACCEPTED;

                                        msg_command_ack.result_param2=MAV_RESULT.MAV_RESULT_ENUM_END;//테스트용 임의 추가
                                        msg_command_ack.progress = MAV_RESULT_IN_PROGRESS;
                                        MAVLinkPacket Mavpacket = msg_command_ack.pack();
                                        byte[] data = Mavpacket.encodePacket();
                                        OutputStream os = socket.getOutputStream();
                                        os.write(data);
                                        os.flush();
                                        break;
                                    }
                                    case MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM: {
                                        System.out.println("메시지 command : "+msg_cmd.command);
                                        msg_command_ack msg_command_ack = new msg_command_ack();
                                        msg_command_ack.command = MAV_CMD_COMPONENT_ARM_DISARM;
                                        msg_command_ack.result = MAV_RESULT.MAV_RESULT_ACCEPTED;
                                        MAVLinkPacket Mavpacket = msg_command_ack.pack();
                                        byte[] data = Mavpacket.encodePacket();
                                        OutputStream os = socket.getOutputStream();
                                        os.write(data);
                                        os.flush();
                                        break;
                                    }
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
