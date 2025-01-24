package mu.utility.service;


import com.apple.itunes.storekit.model.Environment;


public class EnvironmentService {

    private Environment environment;

    public EnvironmentService() {
        this.environment = Environment.SANDBOX; // Assign the value here
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}