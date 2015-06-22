package io.casper.lib.attestations;

public class APIAuth {

    public static final String MASTER_TOKEN = "cp4craTcEr82Pdf5j8mwFKyb8FNZbcel";

    //I was going to implement an Auth system, but that's effort.
    public APIAuth(){

    }

    public boolean isAuthorized(String authToken){
        return authToken.equals(MASTER_TOKEN);
    }

}
