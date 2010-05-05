/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testws;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;
import java.net.URL;

/**
 *
 * @author pvillega
 */
public class Main {

    /**
     * Runs a query against a webservice so it is intercepted by Apache Axis Monitor
     *
     * @param args the command line arguments. [0]: wsdl URL [1]: namespace [2]: server name [3]: fnc name [4]: connect port [5]: (optional) query
     */
    public static void main(String[] args) throws ServiceException, RemoteException, MalformedURLException {
        //run: java -cp /home/pvillega/.m2/repository/axis/axis/1.4/axis-1.4.jar org.apache.axis.utils.tcpmon
        //and set up a proxy on port 8080 to intercept calls
        System.getProperties().setProperty("http.proxySet", "true");
        System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("http.proxyPort", "8080");
        
        if(args.length < 5) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Insuficient parameters. Run: \n java -jar <jar file> <wsdlURL> <namespace> <server name> <fnc name> <connect port> <query (optional)>");
        }

        /*specify wsdl*/
        String wsdlURL = args[0];
        /*specify name space*/
        String nmspace = args[1];
        /*specify target service*/
        String srvname = args[2];
        /*specify target method*/
        String fncname = args[3];
        /*specify connect port*/
        String connectPort = args[4];
        /*specify your query*/
        String query = "";
        if(args.length >= 5) {
            query = args[5];
        }

        
        QName serviceQN = new QName(nmspace, srvname);
        QName portQN = new QName(nmspace, connectPort);
        Service service = new Service(new URL(wsdlURL), serviceQN);        
        Call call = (Call) service.createCall(portQN, fncname);


        String result = "";
        if (!"".equals(query)) {
            result = (String) call.invoke(new Object[]{query});
        } else {
            result = (String) call.invoke(new Object[]{});
        }

        System.out.println(result);
    }
}
