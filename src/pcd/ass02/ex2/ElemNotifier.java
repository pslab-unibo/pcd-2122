package pcd.ass02.ex2;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Component attached to the visitor
 * to notify events about elements 
 * on the event bus.
 * 
 * @author aricci
 *
 */
public class ElemNotifier {

	static public final String NEW_PACKAGE_DECLARED = "new-package-declared";
	static public final String NEW_CLASS_FOUND = "new-class-found";
	static public final String NEW_INTERFACE_FOUND = "new-interface-found";
	static public final String NEW_METHOD_FOUND = "new-method-found";
	static public final String NEW_FIELD_FOUND = "new-field-found";

	private Vertx vertx;
	private String topic;
	
	public ElemNotifier(Vertx vertx, String topic) {
		this.vertx = vertx;
		this.topic = topic;
	}

	public void notifyNewPackageDeclared(String name) {
		JsonObject msg = new JsonObject();
		msg
		.put("event", NEW_PACKAGE_DECLARED)
		.put("name", name);
		vertx.eventBus().publish(topic, msg);
	}

	public void notifyNewClass(String name) {
		JsonObject msg = new JsonObject();
		msg
		.put("event", NEW_CLASS_FOUND)
		.put("name", name);
		vertx.eventBus().publish(topic, msg);
	}

	public void notifyNewInterface(String name) {
		JsonObject msg = new JsonObject();
		msg
		.put("event", NEW_INTERFACE_FOUND)
		.put("name", name);
		vertx.eventBus().publish(topic, msg);
	}

	public void notifyNewField(String name) {
		JsonObject msg = new JsonObject();
		msg
		.put("event", NEW_FIELD_FOUND)
		.put("name", name);
		vertx.eventBus().publish(topic, msg);
	}

	public void notifyNewMethod(String name) {
		JsonObject msg = new JsonObject();
		msg
		.put("event", NEW_METHOD_FOUND)
		.put("name", name);
		vertx.eventBus().publish(topic, msg);
	}

}
