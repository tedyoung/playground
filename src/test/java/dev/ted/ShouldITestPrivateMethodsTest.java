package dev.ted;

import org.junit.jupiter.api.Test;

import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ShouldITestPrivateMethodsTest {

  @Test
  public void callPrivateMethod() throws Exception {
    Scoreboard scoreboard = new Scoreboard();

    Method method = Arrays.stream(Scoreboard.class.getDeclaredMethods())
                          .filter(m -> m.getName().equals("scoredCategoryFor"))
                          .findFirst()
                          .orElseThrow();

    method.setAccessible(true);
    Object returnObj = method.invoke(scoreboard, ScoreCategory.CHOICE);
    System.out.println("Returned: " + returnObj);
  }

  @Test
  public void propertyFinder() throws Exception {
    Constructor<?> declaredConstructor = ScoredCategoryView.class.getDeclaredConstructors()[0];
    declaredConstructor.setAccessible(true);
    ScoredCategoryView view = (ScoredCategoryView) declaredConstructor.newInstance("Description", "1 2 3 4 5", "12", true);

    List<Method> getters = Arrays.stream(ScoredCategoryView.class.getDeclaredMethods())
                             .filter(m -> m.getName().startsWith("get"))
                             .toList();

    Arrays.stream(Introspector.getBeanInfo(ScoredCategoryView.class).getPropertyDescriptors())
          .forEach(System.out::println);

    System.out.println("{");
    for (Method getter : getters) {
      Object returnObj = getter.invoke(view);
      if (returnObj instanceof String) {
        System.out.print("\"" + getter.getName().substring(3, 4).toLowerCase() + getter.getName().substring(4) + "\": ");
        System.out.println("\"" + returnObj + "\",");
      }
    }
    System.out.println("}");
  }
}
