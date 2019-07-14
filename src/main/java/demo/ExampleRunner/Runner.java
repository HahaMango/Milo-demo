package demo.ExampleRunner;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public interface Runner {

    public void Run(OpcUaClient opcUaClient) throws Exception;
}
