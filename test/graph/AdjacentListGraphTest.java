package graph;

import exception.*;
import graph.AdjacentListGraph;
import graph.GenericMatrix;
import graph.Pair;
import graph.Vertex;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AdjacentListGraphTest {
    private AdjacentListGraph<Integer> cities;

    public void setUp1() {
        this.cities = new AdjacentListGraph<>(false, false, false);
    }

    public void setUp3() {
        cities = new AdjacentListGraph<>(false, true, true);
    }

    public void setUp4() {
        cities = new AdjacentListGraph<>(true, true, false);
    }

    @Test
    public void testInsertVertex() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        Integer c6 = 6;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addVertex(c6);
            assertEquals(0, cities.getIndex(c1));
            assertEquals(1, cities.getIndex(c2));
            assertEquals(2, cities.getIndex(c3));
            assertEquals(3, cities.getIndex(c4));
            assertEquals(4, cities.getIndex(c5));
            assertEquals(5, cities.getIndex(c6));
            assertEquals(6, cities.getVertex().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddRepeatedVertex() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
        } catch (Exception e) {
            fail();
        }
        assertThrows(ExistenceVertexException.class, () -> cities.addVertex(c1));
        assertThrows(ExistenceVertexException.class, () -> cities.addVertex(c2));
        assertThrows(ExistenceVertexException.class, () -> cities.addVertex(c3));
        assertThrows(ExistenceVertexException.class, () -> cities.addVertex(c4));
    }

    @Test
    public void testAddingMultipleVertex() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 =  4;
        Integer c5 = 5;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
        } catch (Exception e) {
            fail();
        }
        assertEquals(5, cities.getVertex().size());
    }

    @Test
    public void testAddEdgeInSimpleGraph() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addEdge(c1, c2, 0);
            assertThrows(MultipleEdgesNotAllowedException.class, () -> cities.addEdge(c2, c1, 0));
            assertThrows(LoopNotAllowedException.class, () -> cities.addEdge(c1, c1, 0));
            assertThrows(VertexNotFoundException.class, () -> cities.addEdge(3, c1, 0));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddEdgeInPseudoGraph() {
        setUp3();
        Integer c1 = 1;
        Integer c2 = 2;

        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c2, c1, 2);
            cities.addEdge(c1, c1, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, cities.getVertex().get(cities.getIndex(c1)).searchVertex(c1).getValue());
        assertNull(cities.getVertex().get(cities.getIndex(c2)).searchVertex(c2));
    }

    @Test
    public void testAddEdgeInDirectedGraph() {
        setUp4();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        Integer c6 = 6;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addVertex(c6);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c4, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c1, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c3, c6, 0);
            cities.addEdge(c4, c5, 0);
        } catch (Exception e) {
            fail();
        }
        assertNull(cities.getVertex().get(cities.getIndex(c2)).searchVertex(c1));
        assertEquals(c1, cities.getVertex().get(cities.getIndex(c3)).searchVertex(c1).getValue());//3--1
        assertEquals(c2, cities.getVertex().get(cities.getIndex(c1)).searchVertex(c2).getValue());//1--2
        assertNull(cities.getVertex().get(cities.getIndex(c1)).searchVertex(c3));//1--3
        assertEquals(c3, cities.getVertex().get(cities.getIndex(c2)).searchVertex(c3).getValue());//2--3
        assertEquals(c4, cities.getVertex().get(cities.getIndex(c1)).searchVertex(c4).getValue());//1--4
        assertNull(cities.getVertex().get(cities.getIndex(c2)).searchVertex(c1));//1--5
        assertEquals(c6, cities.getVertex().get(cities.getIndex(c3)).searchVertex(c6).getValue());//3--6
        assertNull(cities.getVertex().get(cities.getIndex(c2)).searchVertex(c6));//2--6
    }

    @Test
    public void testDeleteVertexInDirectedGraph() {
        setUp4();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c4, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c1, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 0);
            cities.deleteVertex(c1);
            cities.deleteVertex(c3);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(3, cities.getVertex().size());
        assertEquals(-1,(cities.getIndex(c1)));
        assertEquals(-1,(cities.getIndex(c3)));
    }

    @Test
    public void testDeleteVertexInPseudoGraph() {
        setUp3();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c4, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 0);
            cities.deleteVertex(c4);
            cities.deleteVertex(c2);
        } catch (Exception e) {
            fail();
        }
        assertThrows(VertexNotFoundException.class, () -> cities.deleteVertex(c4));
        assertEquals(3, cities.getVertex().size());
        assertEquals(-1,(cities.getIndex(c4)));
        assertEquals(-1,(cities.getIndex(c2)));
    }

    @Test
    public void testDeleteVertexInSimpleGraph() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.deleteVertex(c1);
        } catch (Exception e) {
            fail();
        }

        assertEquals(2, cities.getVertex().size());
        assertEquals(-1,(cities.getIndex(c1)));
        assertNull(cities.getVertex().get(cities.getIndex(c2)).searchVertex(c1));
    }

    @Test
    public void testDeleteEdgeInSimpleGraph() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.removeEdge(c1, c2, 0);
            cities.removeEdge(c1, c3, 0);
        } catch (Exception e) {
            fail();
        }
        assertEquals(4, cities.getVertex().size());
        assertEquals(0, cities.getVertex().get(cities.getIndex(c1)).getAdjacentList().size());//c1
        assertEquals(2, cities.getVertex().get(cities.getIndex(c2)).getAdjacentList().size());//c2
        assertNull(cities.getVertex().get(cities.getIndex(c1)).searchVertex(c2));//c1--c2
    }

    @Test
    public void testDeleteEdgeExceptions() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
        } catch (Exception e) {
            fail();
        }
        assertThrows(EdgeNotFoundException.class, () -> cities.removeEdge(c1, c4, 0));
        assertThrows(EdgeNotFoundException.class, () -> cities.removeEdge(c3, c4, 0));
        assertThrows(VertexNotFoundException.class, () -> cities.removeEdge((5), c2, 1));
    }

    @Test
    public void testDeleteEdgeInPseudoGraph() {
        setUp3();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c4, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 2);
            cities.removeEdge(c1, c2, 0);
            cities.removeEdge(c1, c4, 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(5, cities.getVertex().size());
        assertEquals(1, cities.getVertex().get(cities.getIndex(c1)).getAdjacentList().size());//c1
        assertEquals(2, cities.getVertex().get(cities.getIndex(c2)).getAdjacentList().size());//c2
        assertNull(cities.getVertex().get(cities.getIndex(c1)).searchVertex(c2));//c1--c2
        assertEquals(c1, cities.getVertex().get(cities.getIndex(c4)).searchVertex(c1).getValue());//c4--c1
    }

    @Test
    public void testBFSColor() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.BFS(c1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(4, cities.getVertex().size());
        assertEquals(Color.BLACK, cities.getVertex().get(cities.getIndex(c1)).getColor());//c1
        assertEquals(Color.BLACK, cities.getVertex().get(cities.getIndex(c2)).getColor());//c2
        assertEquals(Color.BLACK, cities.getVertex().get(cities.getIndex(c3)).getColor());//c3
        assertEquals(Color.BLACK, cities.getVertex().get(cities.getIndex(c4)).getColor());//c4
    }

    @Test
    public void testBFSParents() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.BFS(c1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(4, cities.getVertex().size());
        assertNull(cities.getVertex().get(cities.getIndex(c1)).getParent());//c1
        assertEquals(c1, cities.getVertex().get(cities.getIndex(c2)).getParent().getValue());//c2
        assertEquals(c1, cities.getVertex().get(cities.getIndex(c3)).getParent().getValue());//c3
        assertEquals(c2, cities.getVertex().get(cities.getIndex(c4)).getParent().getValue());//c4
    }

    @Test
    public void testBFSDistance() {
        setUp1();
        Integer c1 =1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        Integer c6 = 6;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addVertex(c6);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 2);
            cities.addEdge(c4, c6, 0);
            cities.addEdge(c5, c6, 0);
            cities.BFS(c1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(6, cities.getVertex().size());
        assertEquals(0, cities.getVertex().get(cities.getIndex(c1)).getDistance());//c1
        assertEquals(1, cities.getVertex().get(cities.getIndex(c2)).getDistance());//c2
        assertEquals(1, cities.getVertex().get(cities.getIndex(c3)).getDistance());//c3
        assertEquals(1, cities.getVertex().get(cities.getIndex(c4)).getDistance());//c4
        assertEquals(2, cities.getVertex().get(cities.getIndex(c5)).getDistance());//c5
        assertEquals(2, cities.getVertex().get(cities.getIndex(c6)).getDistance());//c6
    }

    @Test
    public void testDFSTime() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        Integer c6 = 6;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addVertex(c6);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 2);
            cities.addEdge(c4, c6, 0);
            cities.addEdge(c5, c6, 0);
            cities.DFS();
        } catch (Exception e) {
            fail();
        }
        assertEquals(6, cities.getVertex().size());
        assertEquals(12, cities.getVertex().get(cities.getIndex(c1)).getTime());
        assertEquals(11, cities.getVertex().get(cities.getIndex(c2)).getTime());
        assertEquals(10, cities.getVertex().get(cities.getIndex(c3)).getTime());
        assertEquals(9, cities.getVertex().get(cities.getIndex(c4)).getTime());
        assertEquals(7, cities.getVertex().get(cities.getIndex(c5)).getTime());
        assertEquals(8, cities.getVertex().get(cities.getIndex(c6)).getTime());
    }

    @Test
    public void testDFSDistance() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 2);
            cities.addEdge(c4, c5, 0);
            cities.DFS();
        } catch (Exception e) {
            fail();
        }
        assertEquals(5, cities.getVertex().size());
        assertEquals(1, cities.getVertex().get(cities.getIndex(c1)).getDistance());//c1---c5
        assertEquals(2, cities.getVertex().get(cities.getIndex(c2)).getDistance());
        assertEquals(3, cities.getVertex().get(cities.getIndex(c3)).getDistance());
        assertEquals(4, cities.getVertex().get(cities.getIndex(c4)).getDistance());
        assertEquals(5, cities.getVertex().get(cities.getIndex(c5)).getDistance());
    }

    @Test
    public void testDFSParents() {
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        Integer c5 = 5;
        Integer c6 = 6;
        Integer c7 = 7;
        try {
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addVertex(c5);
            cities.addVertex(c6);
            cities.addVertex(c7);
            cities.addEdge(c1, c2, 0);
            cities.addEdge(c1, c3, 0);
            cities.addEdge(c2, c3, 0);
            cities.addEdge(c2, c4, 0);
            cities.addEdge(c3, c4, 0);
            cities.addEdge(c3, c5, 0);
            cities.addEdge(c4, c1, 2);
            cities.addEdge(c4, c6, 0);
            cities.addEdge(c5, c6, 0);
            cities.addEdge(c6, c7, 0);
            cities.DFS();
        } catch (Exception e) {
            fail();
        }
        assertEquals(7, cities.getVertex().size());
        assertNull(cities.getVertex().get(cities.getIndex(c1)).getParent());
        assertEquals(cities.getVertex().get(cities.getIndex(c1)), cities.getVertex().get(cities.getIndex(c2)).getParent());//1---2
        assertEquals(cities.getVertex().get(cities.getIndex(c2)), cities.getVertex().get(cities.getIndex(c3)).getParent());//2---3
        assertEquals(cities.getVertex().get(cities.getIndex(c3)), cities.getVertex().get(cities.getIndex(c4)).getParent());//3---4
        assertEquals(cities.getVertex().get(cities.getIndex(c6)), cities.getVertex().get(cities.getIndex(c5)).getParent());//6---5
        assertEquals(cities.getVertex().get(cities.getIndex(c4)), cities.getVertex().get(cities.getIndex(c6)).getParent());//4---6
        assertEquals(cities.getVertex().get(cities.getIndex(c6)), cities.getVertex().get(cities.getIndex(c7)).getParent());//6---7
    }

    @Test
    public void testFloydWarshall(){
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;

        try{
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 1);
            cities.addEdge(c1, c3, 5);
            cities.addEdge(c2, c3, 2);
            cities.addEdge(c2, c4, 2);
            cities.addEdge(c3, c4, 1);
        }catch(Exception e){
            fail();
        }
        Pair<int[][], GenericMatrix<Integer>> floyd = cities.floydWarshall();
        int[][] distances = floyd.getValue1();
        GenericMatrix<Integer> path = floyd.getValue2();
        assertEquals(4, cities.getVertex().size());
        assertEquals(0,distances[0][0]);
        assertEquals(1,distances[0][1]);
        assertEquals(3,distances[0][2]);
        assertEquals(3,distances[0][3]);
        assertEquals(1,distances[1][0]);
        assertEquals(0,distances[1][1]);
        assertEquals(2,distances[1][2]);
        assertEquals(2,distances[1][3]);
        assertEquals(3,distances[2][0]);
        assertEquals(2,distances[2][1]);
        assertEquals(0,distances[2][2]);
        assertEquals(1,distances[2][3]);
        assertEquals(3,distances[3][0]);
        assertEquals(2,distances[3][1]);
        assertEquals(1,distances[3][2]);
        assertEquals(0,distances[3][3]);
        assertEquals(c1,path.get(0,0));
        assertEquals(c1,path.get(0,1));
        assertEquals(c2,path.get(0,2));
        assertEquals(c2,path.get(0,3));
        assertEquals(c2,path.get(1,0));
        assertEquals(c2,path.get(1,1));
        assertEquals(c2,path.get(1,2));
        assertEquals(c2,path.get(1,3));
        assertEquals(c2,path.get(2,0));
        assertEquals(c3,path.get(2,1));
        assertEquals(c3,path.get(2,2));
        assertEquals(c3,path.get(2,3));
        assertEquals(c2,path.get(3,0));
        assertEquals(c4,path.get(3,1));
        assertEquals(c4,path.get(3,2));
        assertEquals(c4,path.get(3,3));
    }

    @Test
    public void testPrim(){
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try{
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 10);
            cities.addEdge(c1, c3, 7);
            cities.addEdge(c2, c3, 5);
            cities.addEdge(c2, c4, 3);

        }catch(Exception e){
            fail();
        }
        cities.prim();
        assertEquals(4, cities.getVertex().size());
        assertNull(cities.getVertex().get(0).getParent());
        assertEquals(c1, cities.getVertex().get(2).getParent().getValue());
        assertEquals(c3,cities.getVertex().get(1).getParent().getValue());
    }

    @Test
    public void testKruskal(){
        setUp1();
        Integer c1 = 1;
        Integer c2 = 2;
        Integer c3 = 3;
        Integer c4 = 4;
        try{
            cities.addVertex(c1);
            cities.addVertex(c2);
            cities.addVertex(c3);
            cities.addVertex(c4);
            cities.addEdge(c1, c2, 10);
            cities.addEdge(c1, c3, 7);
            cities.addEdge(c2, c3, 5);
            cities.addEdge(c2, c4, 3);

        }catch(Exception e){
            fail();
        }
        ArrayList<Pair<Pair<Vertex<Integer>,Vertex<Integer>>,Integer>>mst = cities.kruskal();
        assertEquals(4, cities.getVertex().size());
        assertEquals(c2,mst.get(0).getValue1().getValue1().getValue());
        assertEquals(c4,mst.get(0).getValue1().getValue2().getValue());
        assertEquals(3,(int)mst.get(0).getValue2());
        assertEquals(c2,mst.get(1).getValue1().getValue1().getValue());
        assertEquals(c3,mst.get(1).getValue1().getValue2().getValue());
        assertEquals(5,(int)mst.get(1).getValue2());
        assertEquals(c1,mst.get(2).getValue1().getValue1().getValue());
        assertEquals(c3,mst.get(2).getValue1().getValue2().getValue());
        assertEquals(7,(int)mst.get(2).getValue2());
        assertThrows(IndexOutOfBoundsException.class,()->{
            mst.get(4);
        });

    }
}