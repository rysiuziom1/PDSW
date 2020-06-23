package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Teacher {
    private final String firstName;
    private final String lastName;
    private String ipAddr = new String();

    public Teacher(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            this.ipAddr = socket.getLocalAddress().getHostAddress().toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIpAddr(){
        return ipAddr;
    }
}
