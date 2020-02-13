package webhook.teamcity.payload.variableresolver.velocity;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import webhook.teamcity.Loggers;

import webhook.teamcity.payload.convertor.SuperclassExclusionStrategy;

public class VelocityToJsonDirective extends Directive {

	public static final TypeAdapter<String> stringAdapter = new TypeAdapter<String>() {
		@Override
		public String read(JsonReader in) throws IOException {
			JsonToken peek = in.peek();
			if (peek == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			/* coerce booleans to strings for backwards compatibility */
			if (peek == JsonToken.BOOLEAN) {
				return Boolean.toString(in.nextBoolean());
			}
			return in.nextString();
		}

		@Override
		public void write(JsonWriter out, String value) throws IOException {
			value = value.replaceAll("\\", "\\\\").replaceAll("\"", "\\\"");
			Loggers.SERVER.info(value);
			out.value(value);
		}
	};

	Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
			.addSerializationExclusionStrategy(new SuperclassExclusionStrategy()).setPrettyPrinting()
			.registerTypeAdapter(String.class, stringAdapter).create();

	@Override
	public String getName() {
		return "tojson";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException {
		Object object = null;
		if (node.jjtGetNumChildren() >= 2 && node.jjtGetChild(0) != null && node.jjtGetChild(1) != null) {
			object = node.jjtGetChild(0).value(context);
			String keyName = String.valueOf(node.jjtGetChild(1).value(context));
			writer.write("\"" + keyName + "\" : " + gson.toJson(object));
			return true;
		} else if (node.jjtGetChild(0) != null) {
			object = node.jjtGetChild(0).value(context);
			writer.write(gson.toJson(object));
			return true;
		}
		return false;
	}
}