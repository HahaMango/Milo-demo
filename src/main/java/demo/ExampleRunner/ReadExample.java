package demo.ExampleRunner;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

/**
 * 读数据Demo
 */
public class ReadExample implements Runner {

    @Override
    public void Run(OpcUaClient opcUaClient) throws Exception{
        opcUaClient.connect().get();

        NodeId nodeId = new NodeId(3,"\"test_value\"");
        DataValue value = opcUaClient.readValue(0.0,TimestampsToReturn.Both,nodeId).get();

        System.out.println((Integer)value.getValue().getValue());
    }
}
