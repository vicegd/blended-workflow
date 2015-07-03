package pt.ist.socialsoftware.blendedworkflow.domain;

import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public class DataModel extends DataModel_Base {

    public enum DataState {
        DEFINED, UNDEFINED, SKIPPED
    }

    /**
     * Clone the DataModel.
     */
    public DataModelInstance cloneDataModel() throws BWException {
        DataModelInstance newDataModelInstance = new DataModelInstance();
        Entity relationEntityOne;
        Entity relationEntityTwo;

        for (Entity entity : getEntitiesSet()) {
            entity.cloneEntity(newDataModelInstance);
        }

        // Get relation -> Get new Entities -> Clone with new Entities
        for (Relation relation : getRelationsSet()) {
            relationEntityOne = newDataModelInstance
                    .getEntity(relation.getEntityOne().getName());
            relationEntityTwo = newDataModelInstance
                    .getEntity(relation.getEntityTwo().getName());
            relation.cloneRelation(newDataModelInstance, relationEntityOne,
                    relationEntityTwo);
        }
        return newDataModelInstance;
    }

    public Entity getEntity(String name) {
        for (Entity entity : getEntitiesSet()) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public Relation getRelation(String name) {
        for (Relation relation : getRelationsSet()) {
            if (relation.getName().equals(name)) {
                return relation;
            }
        }
        return null;
    }

    public void delete() {
        getEntitiesSet().stream().forEach(ent -> ent.delete());

        setSpecification(null);
        deleteDomainObject();
    }

    public void createEntity(String entityName) {
        new Entity(this, entityName);
    }

}