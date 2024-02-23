/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void checkConstructor() {
        var e = new Event("title");
        assertEquals("title", e.title);
    }

    @Test
    public void equalsHashCode() {
        var a = new Event("title");
        var b = new Event("title");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Event("title1");
        var b = new Event("title2");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void setIdTest() {
        var a = new Event("title1");
        a.setId(1);
        assertEquals(1, a.getId());
    }

    @Test
    public void setTitleTest() {
        var a = new Event("title1");
        a.setTitle("title2");
        assertEquals("title2", a.getTitle());
    }

    @Test
    public void getTitleTest() {
        var a = new Event("title1");
        assertEquals("title1", a.getTitle());
    }
}
