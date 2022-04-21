package fr.legrain.gwihostingfr.api.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2016-02-24T11:51:42.720+01:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "GandiServiceService", 
                  wsdlLocation = "http://api.gwi-hosting.fr/api/soap/gandi?wsdl",
                  targetNamespace = "http://api.gwi-hosting.fr/api/soap/gandi") 
public class GandiServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://api.gwi-hosting.fr/api/soap/gandi", "GandiServiceService");
    public final static QName GandiServicePort = new QName("http://api.gwi-hosting.fr/api/soap/gandi", "GandiServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://api.gwi-hosting.fr/api/soap/gandi?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GandiServiceService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://api.gwi-hosting.fr/api/soap/gandi?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GandiServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GandiServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GandiServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public GandiServiceService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public GandiServiceService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public GandiServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns GandiServicePort
     */
    @WebEndpoint(name = "GandiServicePort")
    public GandiServicePort getGandiServicePort() {
        return super.getPort(GandiServicePort, GandiServicePort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GandiServicePort
     */
    @WebEndpoint(name = "GandiServicePort")
    public GandiServicePort getGandiServicePort(WebServiceFeature... features) {
        return super.getPort(GandiServicePort, GandiServicePort.class, features);
    }

}