/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBinder {
    private InternalViewBinder internalViewBinder;
    private BindingContext bindingContext;

    public ViewBinder(InternalViewBinder internalViewBinder, BindingContext bindingContext) {
	this.internalViewBinder = internalViewBinder;
	this.bindingContext = bindingContext;
    }

    public View inflateAndBind(int layoutId, String presentationModelPropertyName) {
	Object presentationModel = getPresentationModel(presentationModelPropertyName);
	return internalViewBinder.inflateAndBind(layoutId, presentationModel);
    }

    private Object getPresentationModel(String presentationModelPropertyName) {
	ValueModel<Object> valueModel = bindingContext.getReadOnlyPropertyValueModel(presentationModelPropertyName);
	return valueModel.getValue();
    }

    public View inflate(int layoutId) {
	return internalViewBinder.inflate(layoutId);
    }

}