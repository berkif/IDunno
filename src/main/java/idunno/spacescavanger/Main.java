package idunno.spacescavanger;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import javax.xml.bind.DatatypeConverter;

public class Main {
	public static void main(String[] args) throws IOException, DeploymentException {
		WebSocketContainer webSocket = ContainerProvider.getWebSocketContainer();
		ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
			@Override
			public void beforeRequest(Map<String, List<String>> headers) {
				headers.put("Authorization", Arrays.asList(
					"Basic " + DatatypeConverter.printBase64Binary("idunno:ZkxIGkO1eqvchhj9apcY".getBytes())));
			}
		};
		ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
				.configurator(configurator)
				.build();

		ClientEndpoint client = new ClientEndpoint();
		webSocket.connectToServer(client, config,
			URI.create("ws://javachallenge.loxon.hu:8080/JavaChallenge2018/websocket"));
		System.in.read();
		client.stop();
	}
}
