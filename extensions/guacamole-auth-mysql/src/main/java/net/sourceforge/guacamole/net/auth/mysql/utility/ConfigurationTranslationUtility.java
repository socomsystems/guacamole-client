/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is guacamole-auth-mysql.
 *
 * The Initial Developer of the Original Code is
 * James Muehlner.
 * Portions created by the Initial Developer are Copyright (C) 2010
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package net.sourceforge.guacamole.net.auth.mysql.utility;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.guacamole.net.auth.mysql.model.ConnectionParameter;
import net.sourceforge.guacamole.protocol.GuacamoleConfiguration;

/**
 * Provides functions for translating between GuacamoleConfiguration objects
 * and a collection of ConnectionParameter database records.
 * @author James Muehlner
 */
public class ConfigurationTranslationUtility {

    /**
     * Get a GuacamoleConfiguration based on the provided protocol and parameters.
     * @param protocol the protocol used (VNC, RDP, etc)
     * @param parameters the parameter database records to translate
     * @return
     */
    public GuacamoleConfiguration getConfiguration(String protocol, Iterable<ConnectionParameter> parameters) {
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();
        configuration.setProtocol(protocol);

        for(ConnectionParameter parameter : parameters) {
            configuration.setParameter(parameter.getParameter_name(), parameter.getParameter_value());
        }

        return configuration;
    }

    /**
     * Creates a list of ConnectionParameter database records based on the provided connectionID and GuacamoleConfiguration.
     * @param connectionID the ID of the connection that these parameters are for
     * @param configuration the configuration to pull the parameter values from
     * @return
     */
    public List<ConnectionParameter> getConnectionParameters(int connectionID, GuacamoleConfiguration configuration) {
        List<ConnectionParameter> connectionParameters = new ArrayList<ConnectionParameter>();

        for(String parameterName : configuration.getParameterNames()) {
            ConnectionParameter connectionParameter = new ConnectionParameter();
            String parameterValue = configuration.getParameter(parameterName);
            connectionParameter.setConnection_id(connectionID);
            connectionParameter.setParameter_name(parameterName);
            connectionParameter.setParameter_value(parameterValue);

            connectionParameters.add(connectionParameter);
        }

        return connectionParameters;
    }
}
