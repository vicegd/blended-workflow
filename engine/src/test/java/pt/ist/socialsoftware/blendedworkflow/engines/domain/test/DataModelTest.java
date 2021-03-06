package pt.ist.socialsoftware.blendedworkflow.engines.domain.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.domain.BWEntity;
import pt.ist.socialsoftware.blendedworkflow.domain.BWRelation;
import pt.ist.socialsoftware.blendedworkflow.domain.BWRelation.Cardinality;
import pt.ist.socialsoftware.blendedworkflow.domain.DataModelInstance;
import pt.ist.socialsoftware.blendedworkflow.domain.EntityInstance;
import pt.ist.socialsoftware.blendedworkflow.domain.RelationInstance;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public class DataModelTest extends AbstractDomainTest {

    private DataModelInstance dataModelInstance;

    @Override
    protected void populate4DomainTest() throws BWException {
        // TODO Auto-generated method stub

    }

    @Test
    public void test2() throws BWException {
        dataModelInstance = new DataModelInstance();

        // Types
        BWEntity A = new BWEntity(dataModelInstance, "A", false);
        BWEntity B = new BWEntity(dataModelInstance, "B", false);
        BWEntity C = new BWEntity(dataModelInstance, "C", false);
        BWEntity D = new BWEntity(dataModelInstance, "D", false);
        BWEntity E = new BWEntity(dataModelInstance, "E", false);
        BWEntity F = new BWEntity(dataModelInstance, "F", false);
        BWEntity X = new BWEntity(dataModelInstance, "X", false);
        BWRelation AB = new BWRelation(dataModelInstance, "AB", A, A.getName(),
                Cardinality.ONE, false, B, B.getName(), Cardinality.ZERO_MANY,
                true);
        BWRelation AC = new BWRelation(dataModelInstance, "AC", A, A.getName(),
                Cardinality.ONE, true, C, C.getName(), Cardinality.ONE, false);
        BWRelation AD = new BWRelation(dataModelInstance, "AD", A, A.getName(),
                Cardinality.ONE, true, D, D.getName(), Cardinality.ONE, false);
        BWRelation DE = new BWRelation(dataModelInstance, "DE", D, D.getName(),
                Cardinality.ONE, true, E, E.getName(), Cardinality.ONE, false);
        BWRelation DF = new BWRelation(dataModelInstance, "DF", D, D.getName(),
                Cardinality.ONE, true, F, F.getName(), Cardinality.ONE, false);
        BWRelation EF = new BWRelation(dataModelInstance, "EF", E, E.getName(),
                Cardinality.ONE, true, F, F.getName(), Cardinality.ONE, false);
        BWRelation EC = new BWRelation(dataModelInstance, "EC", E, E.getName(),
                Cardinality.ONE, true, C, C.getName(), Cardinality.ONE, false);

        // Instances
        EntityInstance A1 = new EntityInstance(A);
        EntityInstance B1 = new EntityInstance(B);
        EntityInstance C1 = new EntityInstance(C);
        EntityInstance D1 = new EntityInstance(D);
        EntityInstance E1 = new EntityInstance(E);
        EntityInstance F1 = new EntityInstance(F);
        EntityInstance F2 = new EntityInstance(F);
        EntityInstance F3 = new EntityInstance(F);
        EntityInstance X1 = new EntityInstance(X);
        new RelationInstance(AB, A1, B1, A1.getNewRelationInstanceID());
        new RelationInstance(AC, A1, C1, A1.getNewRelationInstanceID());
        new RelationInstance(AD, A1, D1, A1.getNewRelationInstanceID());
        new RelationInstance(DE, D1, E1, D1.getNewRelationInstanceID());
        new RelationInstance(DF, D1, F3, D1.getNewRelationInstanceID());
        new RelationInstance(EF, E1, F1, E1.getNewRelationInstanceID());
        new RelationInstance(EF, E1, F2, E1.getNewRelationInstanceID());
        new RelationInstance(EC, E1, C1, E1.getNewRelationInstanceID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(A1, F1,
                new ArrayList<EntityInstance>());
        assertEquals(F1, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=A1 & target=F1");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(A1, F2,
                new ArrayList<EntityInstance>());
        // assertEquals(F2, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=A1 & target=F2");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(A1, F3,
                new ArrayList<EntityInstance>());
        assertEquals(F3, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=A1 & target=F3");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(B1, F1,
                new ArrayList<EntityInstance>());
        assertEquals(F1, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=B1 & target=F");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(C1, F1,
                new ArrayList<EntityInstance>());
        assertEquals(F1, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=C1 & target=F1");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(D1, F1,
                new ArrayList<EntityInstance>());
        assertEquals(F1, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=D1 & target=F1");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(E1, F1,
                new ArrayList<EntityInstance>());
        assertEquals(F1, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=E1 & target=F1");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(F1, F1,
                new ArrayList<EntityInstance>());
        assertEquals(F1, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=F1 & target=F1");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        dataModelInstance.clearSearchEntityInstanceVariables();
        dataModelInstance.searchEntityInstance(A1, X1,
                new ArrayList<EntityInstance>());
        assertEquals(null, dataModelInstance.getFoundEntityInstance());
        // System.out.println("SearchEntityInstance: init=A1 & target=X");
        // for (EntityInstance e1 :
        // dataModelInstance.getFoundEntityInstancePath())
        // System.out.println(e1.getID());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(A, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(F, dataModelInstance.getFoundEntity());
        // System.out.println("SearchEntity: init=A & target=F");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());
        // for (Relation r : dataModelInstance.getFoundRelationPath())
        // System.out.println(r.getName());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(B, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(F, dataModelInstance.getFoundEntity());
        // System.out.println("SearchEntity: init=B & target=F");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(C, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(F, dataModelInstance.getFoundEntity());
        // System.out.println("SearchEntity: init=C & target=F");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(D, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(F, dataModelInstance.getFoundEntity());
        // // System.out.println("SearchEntity: init=D & target=F");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(E, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(F, dataModelInstance.getFoundEntity());
        // System.out.println("SearchEntity: init=E & target=F");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(F, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(F, dataModelInstance.getFoundEntity());
        // System.out.println("SearchEntity: init=F & target=F");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());

        // dataModelInstance.clearSearchEntityVariables();
        // dataModelInstance.searchEntity(X, F, null, new ArrayList<Entity>(),
        // new ArrayList<Relation>());
        // assertEquals(null, dataModelInstance.getFoundEntity());
        // System.out.println("SearchEntity: init=A & target=X");
        // for (Entity e : dataModelInstance.getFoundEntityPath())
        // System.out.println(e.getName());
    }

}
