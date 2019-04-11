package PointOfSale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Simple tests of <code>Seller_GUI</code>.
 */

public class Seller_GUITest extends Seller_GUI {

    private Seller_GUI seller = new Seller_GUI();
    private ConsoleOutputCapturer cos = new ConsoleOutputCapturer();
    long wrongId = 1010101055656L;
    long rightId = 1234567890123L;

    /*
    @Before
    public void setUp() {
        listOfArticles.add(new Article(123L, "Some", 0.12));
        listOfArticles.add(new Article(456L, "Thing", 3.45));
        listOfArticles.add(new Article(789L, "Here", 6.78));
    }
    */

    @Test
    public void testProductNotFound() {
        seller.idEnter.setText("" + wrongId);
        cos.start();
        seller.scanActionPerformed(null);
        String actual = cos.stop().trim();
        assertEquals("Product not found!", actual);
    }

    @Test
    public void testInvalidBarCode() {
        seller.idEnter.setText("");
        cos.start();
        seller.scanActionPerformed(null);
        String actual = cos.stop().trim();
        assertEquals("Invalid bar-code!", actual);
    }

    @Test
    public void testAddToArticleList() {
        seller.addToArticleList(Mockito.anyLong(), Mockito.anyString(), Mockito.anyDouble());
        assertEquals(1, listArticlesJList.getComponentCount());
    }
}