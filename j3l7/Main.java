import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        try {
            start(new Tests());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void start(Object o) throws InvocationTargetException, IllegalAccessException {
        Method[] method = o.getClass().getDeclaredMethods();
        short countBS = 0;
        short countAS = 0;
        for (Method m : method) {
            if (m.getAnnotation(BeforeSuite.class) != null) countBS++;
            else if (m.getAnnotation(AfterSuite.class) != null) countAS++;
        }
        if (countAS > 1 || countBS > 1) throw new RuntimeException("BeforeSuit or AfterSuite annotation err");

        Comparator<Method> comparator = (o1, o2) -> {
            int p1 = 0;
            int p2 = 0;

            Test annotationTest1 = o1.getAnnotation(Test.class);
            if (annotationTest1 != null) p1 = annotationTest1.priority();
            Test annotationTest2 = o2.getAnnotation(Test.class);
            if (annotationTest2 != null) p2 = annotationTest2.priority();
            if (p1 < 1 || p1 > 10) p1 = 10;
            if (p2 < 1 || p2 > 10) p2 = 10;

            BeforeSuite annotationBefore1 = o1.getAnnotation(BeforeSuite.class);
            if (annotationBefore1 != null) p1 = annotationBefore1.priority();
            BeforeSuite annotationBefore2 = o2.getAnnotation(BeforeSuite.class);
            if (annotationBefore2 != null) p2 = annotationBefore2.priority();
            AfterSuite annotationAfter1 = o1.getAnnotation(AfterSuite.class);
            if (annotationAfter1 != null) p1 = annotationAfter1.priority();
            AfterSuite annotationAfter2 = o2.getAnnotation(AfterSuite.class);
            if (annotationAfter2 != null) p2 = annotationAfter2.priority();

            if (p1 > p2) return 1;
            if (p1 < p2) return -1;
            return 0;
        };

        Arrays.sort(method, comparator);

        for (Method m : method) {
                m.invoke(o);
        }
    }
}
