/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settype;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashSet;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

/**
 *
 * @author akhfa
 */
public class ReplSet <T>extends ReceiverAdapter{
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
//    final List<String> state=new LinkedList<String>();
    final HashSet<T> state = new HashSet<T>();

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }
    
    public boolean add(T objek)
    {
        synchronized(state) {
            System.out.println("object added");
            return state.add(objek);
        }
    }
    
    public boolean contains(T objek)
    {
        synchronized(state)
        {
            return state.contains(objek);
        }
    }
    
    public boolean remove(T objek)
    {
        synchronized(state)
        {
            return state.remove(objek);
        }
    }

    public void receive(Message msg) {
        String line= msg.getObject().toString();
//        T obj = msg.getSrc() + msg.getObject();
        
        String splitMessage[] = line.split(" ", 3);
        
        if(!splitMessage[0].equals("[" + user_name + "]"))
            System.out.println(line);
        
        if(splitMessage.length == 3)
        {
            T obj = (T)splitMessage[2];
            switch(splitMessage[1].toLowerCase())
            {
                case "/add":
                    if(this.add(obj))
                        System.out.println("\"" + obj.toString() + "\" berhasil ditambahkan.");
                    else
                        System.out.println("\"" + obj.toString() + " tidak berhasil ditambahkan.");
                    break;
                case "/contains":
                    if(this.contains(obj))
                        System.out.println("\"" + obj.toString() + "\" ada di dalam set.");
                    else
                        System.out.println("\"" + obj.toString() + "\" tidak ada di dalam set.");
                    break;
                case "/remove":
                    if(this.remove(obj))
                        System.out.println("\"" + obj.toString() + "\" berhasil dihapus dari set.");
                    else
                        System.out.println("\""+ obj.toString() + "\" tidak ditemukan di dalam set.");
                    break;
                default:
                    System.out.println("Invalid Command");
            }
        }
        else
        {
            System.out.println("Invalid Format");
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        HashSet<T> list = (HashSet<T>)Util.objectFromStream(new DataInputStream(input));
        synchronized(list) {
            state.clear();
            state.addAll(list);
        }
        
        System.out.println("Ada " + list.size() + " data di dalam set:");
        for(T str: list) {
            System.out.println(str.toString());
        }
    }


    private void start() throws Exception {
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        
        ReplSet<String> newSet = new ReplSet<>();
        
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("/quit") || line.startsWith("/exit")) {
                    break;
                }
                line="[" + user_name + "] " + line;
                
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        ReplSet<String> machine = new ReplSet<>();
        machine.start();
    }
}
