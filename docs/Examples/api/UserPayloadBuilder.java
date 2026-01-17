package examples.api;

import org.json.JSONObject;

public class UserPayloadBuilder {

    private String name = "John Doe";
    private String email = "john@example.com";

    public UserPayloadBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserPayloadBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public String build() {
        return new JSONObject()
                .put("name", name)
                .put("email", email)
                .toString();
    }
}
