package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class CobControllerPARMS implements IHController {
    // NOTE: realistically, only cob_spec would use this transformer

    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = new HttpResponse(request.getSocket());
        String[][] parms = request.getParms();
        String body = "";

        body += this.formatParms(parms);
        response.setBody(body);
        return response;
    }

    private String formatParms(String[][] parms) {
        String response = "";
        for (int x = 0; x < parms.length; x++) {
            response += "\n<p>";
            response += parms[x][0];
            if (parms[x][1] != null) {
                response += " = " + parms[x][1];
            }
            response += "</p>";
        }
        return response;
    }
}