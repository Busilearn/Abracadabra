package com.oloh.oloh.domain.api;

import com.oloh.oloh.model.entities.ServerResponse;

/**
 * Created by micka on 15-Sep-17.
 */

public class ServerEvent {
    private ServerResponse serverResponse;

    public ServerEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
