package gr.uoi.ooad.parser;

import static gr.uoi.ooad.parser.tree.RelationshipType.EXTENSION;
import static gr.uoi.ooad.parser.tree.RelationshipType.IMPLEMENTATION;
import static org.junit.jupiter.api.Assertions.*;

import gr.uoi.ooad.parser.factory.Parser;
import gr.uoi.ooad.parser.factory.ParserType;
import gr.uoi.ooad.parser.factory.ProjectParserFactory;
import gr.uoi.ooad.parser.tree.LeafNode;
import gr.uoi.ooad.parser.tree.PackageNode;
import gr.uoi.ooad.parser.tree.Relationship;
import gr.uoi.ooad.utils.PathTemplate.ParserTesting;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RelationshipIdentifierTest {

    ParserType parserType = ParserType.JAVAPARSER;
    Map<LeafNode, Set<Relationship<LeafNode>>> relationships;
    Map<Path, PackageNode> packages;

    @BeforeEach
    public void setup() {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        packages = parser.parseSourcePackage(ParserTesting.SRC.path);
        relationships = parser.createRelationships(packages);
    }

    @Test
    void implementingClassExtendsExtensionClass() {
        PackageNode inheritancePackage = packages.get(ParserTesting.SRC.path);
        LeafNode leafNode = inheritancePackage.getLeafNodes().get("ImplementingClass");

        // keep all leafNode relationships
        Set<Relationship<LeafNode>> leafNodeRelationships = relationships.get(leafNode);

        boolean isRelationship =
                leafNodeRelationships.stream()
                        .anyMatch(
                                r ->
                                        r.endingNode().nodeName().equals("ExtensionClass")
                                                && r.relationshipType().equals(EXTENSION));

        assertTrue(isRelationship);
    }


    @Test
    void implementingClassImplementsTestingInterface(){
        PackageNode inheritancePackage = packages.get(ParserTesting.SRC.path);
        LeafNode leafNode = inheritancePackage.getLeafNodes().get("ImplementingClass");

        Set<Relationship<LeafNode>> leafNodeRelationships = relationships.get(leafNode);

        // TestingInterface
        boolean isImplementation = leafNodeRelationships.stream().anyMatch(r -> r.endingNode().nodeName().equals("TestingInterface") && r.relationshipType().equals(IMPLEMENTATION));

        assertTrue(isImplementation);
    }


    @Test
    void implementingClassImplementsTestingInterface2(){
        PackageNode inheritancePackage = packages.get(ParserTesting.SRC.path);
        LeafNode leafNode = inheritancePackage.getLeafNodes().get("ImplementingClass");

        Set<Relationship<LeafNode>> leafNodeRelationships = relationships.get(leafNode);

        // TestingInterface2
        boolean isImplementation2 = leafNodeRelationships.stream().anyMatch(r -> r.endingNode().nodeName().equals("TestingInterface2") && r.relationshipType().equals(IMPLEMENTATION));

        assertTrue(isImplementation2);
    }

}
