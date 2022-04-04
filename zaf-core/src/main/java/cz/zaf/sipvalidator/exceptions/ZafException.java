package cz.zaf.sipvalidator.exceptions;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;
import cz.zaf.sipvalidator.nsesss2017.EntityId;

public class ZafException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    final ErrorCode errorCode;
    String mistoChyby;

    List<EntityId> entityIds = new ArrayList<>();

    public ZafException(final ErrorCode errorCode, final String detailChyby) {
        super(detailChyby);
        this.errorCode = errorCode;
    }

    public ZafException(final ErrorCode errorCode, final String detailChyby, final String mistoChyby) {
        super(detailChyby);
        this.errorCode = errorCode;
        this.mistoChyby = mistoChyby;
    }

    public ZafException(final ErrorCode errorCode, final String detailChyby, final String mistoChyby, Throwable cause) {
        super(detailChyby, cause);
        this.errorCode = errorCode;
        this.mistoChyby = mistoChyby;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMistoChyby() {
        return mistoChyby;
    }

    public ZafException setMistoChyby(String mistoChyby) {
        this.mistoChyby = mistoChyby;
        return this;
    }

    public ZafException addEntity(EntityId entityId) {
        entityIds.add(entityId);
        return this;
    }
    
    public ZafException addEntity(List<EntityId> listEntityId) {
        entityIds.addAll(listEntityId);
        return this;
    }

    public List<EntityId> getEntityIds() {
        return entityIds;
    }

}
