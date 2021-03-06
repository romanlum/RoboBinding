/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractVisibilityAttribute.IntegerVisibilityAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class IntegerVisibilityAttributeTest extends AbstractPropertyViewAttributeTest<View, IntegerVisibilityAttribute> {
    private AbstractVisibility visibility;

    @Test
    public void whenUpdatingValueModel_thenViewShouldReflectChanges() {
	int visibilityValue = RandomValues.anyVisibility();

	attribute.valueModelUpdated(visibilityValue);

	verify(visibility).setVisibility(visibilityValue);
    }

    @Override
    protected IntegerVisibilityAttribute createAttribute() {
	visibility = mock(AbstractVisibility.class);
        return new IntegerVisibilityAttribute(visibility);
    }
}
