package stacktype;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Stack;
import java.util.List;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

/**
 *
 * @author Imballinst
 * @param <T>
 */
public class ReplStack<T> extends ReceiverAdapter {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final Stack<T> state2 = new Stack<>();

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        String parse[] = line.split(" ", 3);
        try {
            synchronized(state2) {
                switch(parse[1]) {
                    case "/push":
                        T obj = (T) parse[2];
                        System.out.println(parse[0] + " pushes " + obj + " to the stack");
                        state2.push(obj);
                        break;
                    case "/pop":
                        T obj2 = state2.pop();
                        System.out.println(parse[0] + " pops " + obj2 + " from the stack");
                        break;
                    case "/top":
                        T obj3 = state2.peek();
                        System.out.println(parse[0] + " sees " + obj3 + " from the top of the stack");
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
                System.out.print("> "); System.out.flush();
            }
        } catch (Exception e) {
            switch(parse[1]) {
                case "/pop":
                    System.out.println(parse[0] + " tries to pop but stack is empty");
                    break;
                case "/top":
                    System.out.println(parse[0] + " tries to see the top of the stack but stack is empty");
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
            System.out.print("> "); System.out.flush();
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state2) {
            Util.objectToStream(state2, new DataOutputStream(output));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state2) {
            state2.clear();
            for (String element : list) {
                T el = (T) element;
                state2.push(el);
            }
        }
        System.out.println("received state (" + list.size() + " messages in chat history):");
        for(String str: list) {
            System.out.println(str);
        }
        if (state2.empty())
            System.out.println("empty");
    }

    void start() throws Exception {
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster22");
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ReplStack<String> r = new ReplStack<>();
        r.start();
    }
}