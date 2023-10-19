package com.cheatbreaker.client.util;

import com.google.gson.*;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

public class LunarClientJsonThing implements JsonDeserializer, JsonSerializer {
    public ServerStatusResponse deserialize(JsonElement object, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject = JsonUtils.getJsonElementAsJsonObject(object, "status");
        ServerStatusResponse serverStatusResponse = new ServerStatusResponse();
        if (jsonObject.has("description")) {
            serverStatusResponse.func_151315_a(context.deserialize(jsonObject.get("description"), IChatComponent.class));
        }
        if (jsonObject.has("players")) {
            serverStatusResponse.func_151319_a(context.deserialize(jsonObject.get("players"), ServerStatusResponse.PlayerCountData.class));
        }
        if (jsonObject.has("version")) {
            serverStatusResponse.func_151321_a(context.deserialize(jsonObject.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class));
        }
        if (jsonObject.has("favicon")) {
            serverStatusResponse.func_151320_a(JsonUtils.getJsonObjectStringFieldValue(jsonObject, "favicon"));
        }
        if (jsonObject.has("lcServer")) {
            serverStatusResponse.lcString = jsonObject.get("lcServer").getAsString();
        }
        return serverStatusResponse;
    }

    public JsonElement serialize(ServerStatusResponse src, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (src.func_151317_a() != null) {
            jsonObject.add("description", context.serialize(src.func_151317_a()));
        }
        if (src.func_151318_b() != null) {
            jsonObject.add("players", context.serialize(src.func_151318_b()));
        }
        if (src.func_151322_c() != null) {
            jsonObject.add("version", context.serialize(src.func_151322_c()));
        }
        if (src.func_151316_d() != null) {
            jsonObject.addProperty("favicon", src.func_151316_d());
        }
        return jsonObject;
    }


    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        return serialize((ServerStatusResponse) src, typeOfSrc, context);
    }
}
