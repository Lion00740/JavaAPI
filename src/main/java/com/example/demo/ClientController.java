package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class ClientController {
    protected void sendPacket()
    {
        //отправка пакета серверу номера видео
        DatagramPacket datagramPacket;
        String mediaNumberStr;
        mediaNumberStr = String.valueOf(APIController.mediaNumber);
        byte[] dataBuffer = mediaNumberStr.getBytes();
        datagramPacket = new DatagramPacket(dataBuffer, dataBuffer.length, MainWindow.inetAddress, 8080);

        try {
            MainWindow.datagramSocket.send(datagramPacket);

        } catch (IOException e) {
            System.out.println("Файл не отправился");
            throw new RuntimeException(e);
        }
    }
    protected String receiveNameFile() {
        //получение пакета от сервера с названием файла

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        String videoName;
        try {
            MainWindow.datagramSocket.receive(receivePacket);

            videoName = new String(receivePacket.getData(),0, receivePacket.getLength());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return videoName;
    }
    protected File receiveFile(String nameFile) {
        //получение видео от сервера в виде нескольких пакетов

        byte[] receiveData = new byte[65507];
        FileOutputStream fos;
        File temporary = new File("media/" + nameFile);

        try {
            fos = new FileOutputStream(temporary);

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                MainWindow.datagramSocket.receive(receivePacket);

                byte[] packetData = receivePacket.getData();

                fos.write(packetData, 0, receivePacket.getLength());

                if (receivePacket.getLength() < 65507)
                {
                    fos.write(packetData, 0, receivePacket.getLength());
                    break;
                }
            }

            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temporary;
    }
}
