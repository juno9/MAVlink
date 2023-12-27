package MAVLinkTCP;

import com.MAVLink.Arm;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.Parser;
import com.MAVLink.Takeoff;
import com.MAVLink.common.msg_altitude;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_RESULT;
import com.MAVLink.common.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.PortUnreachableException;
import java.net.Socket;

import static com.MAVLink.common.msg_command_ack.MAVLINK_MSG_ID_COMMAND_ACK;
import static com.MAVLink.common.msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
import static com.MAVLink.enums.MAV_CMD.MAV_CMD_NAV_TAKEOFF;
import static com.MAVLink.enums.MAV_COMPONENT.MAV_COMP_ID_AUTOPILOT1;


public class MAVLinkTCP_Client {


    public static void main(String[] args) {
        String ip = "192.168.0.108";//연결할 주소
        Socket socket;
        int port = 10000;//연결할 포트

        System.out.println("<<Client>>");
        try {
            socket = new Socket(ip, port);//연결할 주소와 포트를 매개변수로 소켓 객체를 생성함
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//서버에서 보내온 데이터를 읽어올 객체
            BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//자동으로 버퍼를 비워 flush를 사용하지 않아도 된다
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//콘솔에 입력한 데이터를 읽어줄 버퍼리더

            System.out.println("전송할 메시지를 입력하세요");

            // 클라이언트에서 입력받은 메시지를 서버로 전송, 별도의 쓰레드를 돌린다
            Thread sendMessageThread = new Thread(() -> {//
                while (true) {//메시지 한번만 보내고 말거 아니니까 반복문 계속 돌도록
                    try {
                        System.out.println("메시지 유형 ");
                        System.out.println("1. arming ");
                        System.out.println("2. takeoff ");
                        System.out.println("입력: ");
                        String message2 = consoleReader.readLine();//콘솔에서 입력값 읽어온 다음 스트링에 할당


                        if (message2.equals("arming")) {
                            Arm arm=new Arm(4,false);//저는 4번 기체이고 모터는 돌고있지 않습니다. 라고 관제에 알리고자 함
                            byte[] data = arm.getRawData();//이 arm객체를 패킷에 담고 메시지가 담긴 패킷을 바이트단위로 쪼갠 다음 data라는 바이트 배열에 담는다.
                            OutputStream os = socket.getOutputStream();
                            os.write(data);//아웃풋 스트림에 흘려보냄
                            os.flush();
                        } else if (message2.equals("takeoff")) {
                            Takeoff takeoff=new Takeoff(4,20);//저는 4번 기체이고 이륙고도는 20을 희망 합니다. 라고 관제에 알리고자 함
                            byte[] data = takeoff.getRawData();
                            OutputStream os = socket.getOutputStream();
                            os.write(data);
                            os.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sendMessageThread.start();//쓰레드 시작

            // 서버에서 온 메시지를 출력, 별도의 쓰레드가 돌면서 메시지를 받아온다
            Thread receiveMessageThread = new Thread(() -> {
                Parser mMavlinkParser=new Parser();//파서 - 바이트단위로 들어온 데이터를 마브링크 형식에 맞는 유용한 형태로 만들어 주는 객체, 파싱한다는 것은 분석한다는 것.
                try {
                    while (true) {
                        System.out.println("");
                        byte[] input = new byte[300];//1바이트짜리 객체를 300개 선언한다. 300인 이유는 마브링크 패킷이 최대 280바이트 이기 때문
                        //읽는 곳의 처리가 쓰는곳보다는 빨라야 한다.
                        //데이터에 따라 데이터 길이를 적절하게 맞춰줘야 한다.
                        //300은 너무 적다

                        int bytesRead = bis.read(input);//읽어온 데이터는 input에 저장되고 bytesRead에는 배열의 길이가 저장된다
                        for (int i = 0; i < bytesRead; i++) {//배열의 길이만큼 반복하며 메시지를 파싱한다 바이트로 전달받은 데이터를 배열에 집어넣고

                            System.out.println("반복횟수 :" + (i+1));
                            MAVLinkPacket packet = mMavlinkParser.mavlink_parse_char(input[i] & 0xff); // & : 비트 연산(논리 곱)
                            //배열에 있는 아이템을 파싱(분석)하여 패킷으로 선언한다.->바이트 데이터를 파싱을통해 유의미한 데이터로 가공한다.
                            // 한 바이트씩 받아서 프레임에 맞게 재구성하는 것이 파싱이다.(중요)
                            //언싸인드 값이 많다. 컴퓨터 구조 자바는 언싸인드가 없다. 보낼 때 언싸인드 값으로 만들기 위해, IOT나 임베디드 쪽은 언싸인드 많이 쓴다. 마이너스 값을


                            if (packet != null) {
                                MAVLinkMessage msg = packet.unpack();//패킷의 필드중에 페이로드 만을 추출해 내는 코드, 메시지 ID에 따라 분기처리 되어 있다.
                                System.out.println("서버로부터 받은 마브링크 메시지 : " + msg.toString()+"\n 패킷 : "+packet);//처음 받은 마브링크 메시지
                                if (msg.msgid == MAVLINK_MSG_ID_COMMAND_ACK) {//만약 메세지 ID가 command_ack 라면
                                    msg_command_ack msg_ack = (msg_command_ack) msg;//서버가 보내온 마브링크 메시지를 ack로 재선언 함
                                    //FC로 커맨드 롱이나 커맨드 인트를 보내면 FC펌웨어에서는 커맨드 애크를 결과값 개념으로 돌려준다(약속)


                                    System.out.println("서버로부터 받은 msg_command_ack : " + msg_ack.toString()
                                            + "\nmsg_ack의 msgid : "+msg_ack.msgid
                                            + "\nmsg_ack의 command : "+msg_ack.command
                                            );


                                    switch (msg_ack.command) {//받은 커맨드에 따라 분기 완료한 행동이 무엇인지 알려주는 것
                                        case MAV_CMD_NAV_TAKEOFF: {
                                            System.out.println("서버로부터 받은메시지 command : "+msg_ack.command);
                                            break;
                                        }
                                        case MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM: {
                                            System.out.println("서버로부터 받은메시지 command : "+msg_ack.command);
                                            break;
                                        }
                                    }
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
