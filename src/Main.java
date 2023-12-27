

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String transferProtocol;
        int port = 10000;

        System.out.println("마브링크 메시지 전송 샘플");


        while (true) {
            System.out.println("TCP / UDP 중 어떤 방식으로 데이터를 전송 하시겠습니까? ");
            System.out.println("1. TCP ");
            System.out.println("2. UDP ");
            transferProtocol = scanner.nextLine();

            if (transferProtocol.equals("TCP")) {
                break;
            } else if (transferProtocol.equals("UDP")) {
                break;
            } else {
                System.out.println("다시 입력해 주세요");

            }
        }

        if (transferProtocol.equals("TCP")) {

            System.out.println("TCP 선택됨");
            try {
                ServerSocket serverSocket = new ServerSocket(port);//접속을 대기할 서버소켓을 열어줌 10000번 포트로 열어준다
                while(true){
                    Socket socket=serverSocket.accept();//서버소켓에 연결을 시도하는 클라이언트와 연결된 소켓이 만들어짐

                    InputStream is=socket.getInputStream();
                    BufferedInputStream bis=new BufferedInputStream(is);
                    DataInputStream dis=new DataInputStream(bis);//클라이언트로부터 데이터를 받아올 객체

                    OutputStream os=socket.getOutputStream();
                    BufferedOutputStream bos=new BufferedOutputStream(os);
                    DataOutputStream dos=new DataOutputStream(bos);//클라이언트에게 데이터를 내보낼 객체
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (transferProtocol.equals("UDP")) {

            System.out.println("UDP 선택됨");

        }


    }
}