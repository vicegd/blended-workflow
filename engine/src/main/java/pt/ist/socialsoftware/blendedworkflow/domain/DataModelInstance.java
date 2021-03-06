package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.ArrayList;

public class DataModelInstance extends DataModelInstance_Base {

    // Search GlobalVariables
    private EntityInstance foundEntityInstance = null;
    private ArrayList<EntityInstance> foundEntityInstancePath = new ArrayList<EntityInstance>();
    private final ArrayList<EntityInstance> visitedEntityInstanceNodes = new ArrayList<EntityInstance>();

    private BWEntity foundEntity = null;
    private ArrayList<BWEntity> foundEntityPath = new ArrayList<BWEntity>();
    private ArrayList<BWRelation> foundRelationPath = new ArrayList<BWRelation>();
    private final ArrayList<BWEntity> visitedEntityNodes = new ArrayList<BWEntity>();

    /**********************
     * Getters and Setters
     **********************/
    public EntityInstance getFoundEntityInstance() {
        return this.foundEntityInstance;
    }

    public ArrayList<EntityInstance> getFoundEntityInstancePath() {
        return this.foundEntityInstancePath;
    }

    public ArrayList<EntityInstance> getVisitedEntityInstanceNodes() {
        return this.visitedEntityInstanceNodes;
    }

    public void clearSearchEntityInstanceVariables() {
        this.foundEntityInstance = null;
        this.foundEntityInstancePath.clear();
        this.visitedEntityInstanceNodes.clear();
    }

    public BWEntity getFoundEntity() {
        return foundEntity;
    }

    public ArrayList<BWEntity> getFoundEntityPath() {
        return foundEntityPath;
    }

    public ArrayList<BWRelation> getFoundRelationPath() {
        return foundRelationPath;
    }

    public ArrayList<BWEntity> getVisitedEntityNodes() {
        return visitedEntityNodes;
    }

    public void clearSearchEntityVariables() {
        this.foundEntity = null;
        this.foundEntityPath.clear();
        this.visitedEntityNodes.clear();
    }

    /********************
     * Search Algorithms
     ********************/
    public void searchEntity(BWEntity initNode, BWEntity targetNode, BWRelation edge,
            ArrayList<BWEntity> currentEntityPath,
            ArrayList<BWRelation> currentRelationPath) {
        visitedEntityNodes.add(initNode);

        if (foundEntityInstance == null) {
            currentEntityPath.add(initNode);
            if (edge != null) {
                currentRelationPath.add(edge);
            }
        }

        if (initNode.equals(targetNode)) {
            foundEntity = initNode;
            this.foundEntityPath = currentEntityPath;
            this.foundRelationPath = currentRelationPath;
        } else {
            for (BWRelation relation : initNode.getRelationsSet()) {
                BWEntity one = relation.getEntityOne();
                BWEntity two = relation.getEntityTwo();

                BWEntity newInitNode = null;
                if (initNode.equals(one)) {
                    newInitNode = two;
                } else {
                    newInitNode = one;
                }

                if (!visitedEntityNodes.contains(newInitNode)) {
                    searchEntity(newInitNode, targetNode, relation,
                            currentEntityPath, currentRelationPath);
                }
            }

            if (foundEntity == null) {
                currentEntityPath.subList(currentEntityPath.size() - 1,
                        currentEntityPath.size()).clear();
                if (!currentRelationPath.isEmpty()) {
                    currentRelationPath.subList(currentRelationPath.size() - 1,
                            currentRelationPath.size()).clear();
                }
            }
        }
    }

    public void searchEntityInstance(EntityInstance initNode,
            EntityInstance targetNode, ArrayList<EntityInstance> currentPath) {
        visitedEntityInstanceNodes.add(initNode);

        if (foundEntityInstance == null) {
            currentPath.add(initNode);
        }

        if (initNode.equals(targetNode)) {
            foundEntityInstance = initNode;
            this.foundEntityInstancePath = currentPath;

        } else {
            for (RelationInstance relationInstance : initNode
                    .getEntityInstanceOneRelationInstancesSet()) {
                EntityInstance two = relationInstance.getEntityInstanceTwo();
                if (!visitedEntityInstanceNodes.contains(two)) {
                    searchEntityInstance(two, targetNode, currentPath);
                }
            }

            for (RelationInstance relationInstance : initNode
                    .getEntityInstanceTwoRelationInstancesSet()) {
                EntityInstance one = relationInstance.getEntityInstanceOne();
                if (!visitedEntityInstanceNodes.contains(one)) {
                    searchEntityInstance(one, targetNode, currentPath);
                }
            }

            if (foundEntityInstance == null) {
                currentPath.subList(currentPath.size() - 1, currentPath.size())
                        .clear();
            }
        }
    }

    public EntityInstance getEntityInstance(EntityInstance startEntityInstance,
            EntityInstance targetEntityInstance) {
        searchEntityInstance(startEntityInstance, targetEntityInstance,
                new ArrayList<EntityInstance>());
        EntityInstance result = getFoundEntityInstance();
        clearSearchEntityInstanceVariables();
        return result;
    }

    public BWEntity getEntity(BWEntity startEntity, BWEntity targetEntity) {
        searchEntity(startEntity, targetEntity, null, new ArrayList<BWEntity>(),
                new ArrayList<BWRelation>());
        BWEntity result = getFoundEntity();
        clearSearchEntityVariables();
        return result;
    }

    public ArrayList<BWRelation> getRelations(BWEntity startEntity,
            BWEntity targetEntity) {
        searchEntity(startEntity, targetEntity, null, new ArrayList<BWEntity>(),
                new ArrayList<BWRelation>());
        ArrayList<BWRelation> result = getFoundRelationPath();
        clearSearchEntityVariables();
        return result;
    }

    // FIXME: Merge with RelationInstance construtor?
    public void createRelationInstance(BWInstance bwInstance, EntityInstance e1,
            EntityInstance e2) {
        DataModelInstance dataModelInstance = bwInstance.getDataModelInstance();

        BWEntity entity1 = e1.getEntity();
        BWEntity entity2 = e2.getEntity();
        boolean exists = false;

        // Check if relation instance already exists
        for (BWRelation relation : dataModelInstance.getRelationsSet()) {
            for (RelationInstance relationInstance : relation
                    .getRelationInstancesSet()) {
                EntityInstance one = relationInstance.getEntityInstanceOne();
                EntityInstance two = relationInstance.getEntityInstanceTwo();
                if (one.equals(e1) && two.equals(e2)
                        || one.equals(e2) && two.equals(e1)) {
                    exists = true;
                    break;
                }
            }
        }

        // Create only if no previous RelationInstance exists
        if (!exists) {
            for (BWRelation relation : dataModelInstance.getRelationsSet()) {
                BWEntity one = relation.getEntityOne();
                BWEntity two = relation.getEntityTwo();
                if ((one.equals(entity1) && two.equals(entity2))) {
                    new RelationInstance(relation, e1, e2,
                            e1.getNewRelationInstanceID());
                } else if (one.equals(entity2) && two.equals(entity1)) {
                    new RelationInstance(relation, e2, e1,
                            e2.getNewRelationInstanceID());
                }
            }
        }
    }

}