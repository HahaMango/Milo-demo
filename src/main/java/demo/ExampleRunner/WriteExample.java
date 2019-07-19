package demo.ExampleRunner;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

public class WriteExample implements Runner {
    @Override
    public void Run(OpcUaClient opcUaClient) throws Exception {
        opcUaClient.connect().get();
        //写入值
        int v = 1;

        NodeId nodeId = new NodeId(3,"\"test_value\"");
        Variant value = new Variant(v);
        DataValue dataValue = new DataValue(value,null,null);

        StatusCode statusCode = opcUaClient.writeValue(nodeId,dataValue).get();

        System.out.println(statusCode.isGood());
    }
}
