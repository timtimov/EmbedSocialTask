package task;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Eftim
 */
public class FilterTest {
    
    Filter instance;
    public FilterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Filter();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of filterMinRating method, of class Filter.
     */
    @Test
    public void testFilterMinRating() {
        instance.filterMinRating("5");
        assertEquals(instance.filtList.size(), 4);
    }

    /**
     * Test of dateOrder method, of class Filter.
     */
    @Test
    public void testDateOrder() {
        instance.filterMinRating("5");
        instance.dateOrder("Newest First");
        assertEquals(instance.filtList.get(0).getString("reviewCreatedOnDate"), "2021-01-25T13:00:35+00:00");
        
    }

    /**
     * Test of ratingOrder method, of class Filter.
     */
    @Test
    public void testRatingOrder() {
        instance.filterMinRating("3");
        instance.ratingOrder("Lowest First");
        assertEquals(instance.filtList.get(0).getInt("rating"), 3);
    }

    /**
     * Test of textOrder method, of class Filter.
     */
    @Test
    public void testTextOrder() {
        instance.filterMinRating("3");
        instance.textOrder("Yes");
        int lastElemPos = instance.filtList.size() - 1;
        assertEquals(instance.filtList.get(lastElemPos).getString("reviewText"), "");
    }

    /**
     * Test of textShown method, of class Filter.
     */
    @Test
    public void testTextShown() {
        instance.filterMinRating("5");
        instance.dateOrder("Oldest First");
        instance.ratingOrder("Highest First");
        instance.textOrder("Yes");
        StringBuilder sb = new StringBuilder();
        for (JSONObject obj : instance.filtList) {
            sb.append(obj.toString(5)).append("\n");
        }
        instance.textShown();
        assertEquals(instance.sb.toString(), sb.toString());
    }
    
}
