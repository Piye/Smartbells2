package teameleven.smartbells2.businesslayer.synchronization;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * This class treats the account of user of the Smart bells system
 * Dummy Authenticator class
 */
public class Authenticator extends AbstractAccountAuthenticator {
    /**
     * Constructor of Authenticator with Context
     * @param context : Context
     */
    public Authenticator(Context context) {
        super(context);
    }

    /**
     * Edit the properties of an account
     * @param response : AccountAuthenticatorResponse
     * @param accountType : Account Type
     * @return null
     */
    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    /**
     * Add an account
     * @param response : AccountAuthenticatorResponse
     * @param accountType : Account Type
     * @param authTokenType : Authenticator Token Type
     * @param requiredFeatures : Array of string of Require Features
     * @param options : Bundle
     * @return null
     * @throws NetworkErrorException
     */
    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse response,
            String accountType, String authTokenType,
            String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        return null;
    }

    /**
     * Confirm the credentials by parameters
     * @param response : AccountAuthenticatorResponse
     * @param account  : Account of user
     * @param options  : Bundle of options
     * @return null
     * @throws NetworkErrorException : Exception of networking errors
     */
    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                     Account account,
                                     Bundle options) throws NetworkErrorException {
        return null;
    }

    /**
     * To get Authentication Token by the parameter values
     * @param response : AccountAuthenticatorResponse
     * @param account  : Account of user
     * @param authTokenType : Authentication's type
     * @param options : Bundle of options
     * @return : null
     * @throws NetworkErrorException
     */
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType,
                               Bundle options) throws NetworkErrorException {
        return null;
    }

    /**
     * To get the authentication Token Level by authentication token type
     * @param authTokenType : Authentication Token Type
     * @return : null
     */
    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    /**
     * Update a credential records by the parameters
     * @param response : AccountAuthenticatorResponse
     * @param account  : Account of user
     * @param authTokenType : Authentication Token Type
     * @param options : Bundle of options
     * @return : null
     * @throws NetworkErrorException
     */
    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                    Account account, String authTokenType,
                                    Bundle options) throws NetworkErrorException {
        return null;
    }

    /**
     * To get Features by parameter values
     * @param response : AccountAuthenticatorResponse
     * @param account  : Account of user
     * @param features : List of array of features
     * @return : null
     * @throws NetworkErrorException : Exception of Networking Errors
     */
    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response,
                              Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
