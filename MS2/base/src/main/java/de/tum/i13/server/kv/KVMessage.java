package de.tum.i13.server.kv;

public interface KVMessage {

    public enum StatusType {
        DELETE, /* Delete - request */
        DELETE_ERROR, /* Delete - request successful */
        DELETE_CACHE_ERROR,
        DELETE_SUCCESS, /* Delete - request successful */
        GET, /* Get - request */
        GET_ERROR, /* requested tuple (i.e. value) not found */
        GET_CACHE_ERROR,
        GET_SUCCESS, /* requested tuple (i.e. value) found */
        PUT, /* Put - request */
        PUT_ERROR, /* Put - request not successful */
        PUT_CACHE_ERROR,
        PUT_SUCCESS, /* Put - request successful, tuple inserted */
        PUT_UPDATE /* Put - request successful, i.e. value updated */

    }

    /**
     * @return the key that is associated with this message,
     * null if not key is associated.
     */
    public String getKey();

    /**
     * @return the value that is associated with this message,
     * null if not value is associated.
     */
    public String getValue();

    /**
     * @return a status string that is used to identify request types,
     * response types and error types associated to the message.
     */
    public StatusType getStatus();

}
