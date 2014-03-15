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
package org.robobinding.viewattribute.edittext;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.google.common.base.Defaults;
import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import static org.apache.commons.lang3.ClassUtils.primitiveToWrapper;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayTextAttribute extends AbstractMultiTypePropertyViewAttribute<EditText> {
    ValueCommitMode valueCommitMode = ValueCommitMode.ON_CHANGE;

    @Override
    protected AbstractPropertyViewAttribute<EditText, ?> createPropertyViewAttribute(Class<?> propertyType) {

        GenericTwoWayTextAttribute attribute=null;
        if (String.class.isAssignableFrom(propertyType)) {
            attribute=new GenericTwoWayTextAttribute<String>(String.class);
        } else if (CharSequence.class.isAssignableFrom(propertyType)) {
            attribute=new GenericTwoWayTextAttribute<CharSequence>(CharSequence.class);
        } else if(long.class.isAssignableFrom(propertyType)){
            attribute=new GenericTwoWayTextAttribute<Long>(Long.class);
        } else if(float.class.isAssignableFrom(propertyType)){
            attribute=new GenericTwoWayTextAttribute<Float>(Float.class);
        } else if(int.class.isAssignableFrom(propertyType)){
            attribute=new GenericTwoWayTextAttribute<Integer>(Integer.class);
        }else if(Long.class.isAssignableFrom(propertyType)){
            attribute=new GenericTwoWayTextAttribute<Long>(Long.class);
        } else if(Float.class.isAssignableFrom(propertyType)){
            attribute=new GenericTwoWayTextAttribute<Float>(Float.class);
        } else if(Integer.class.isAssignableFrom(propertyType)){
            attribute=new GenericTwoWayTextAttribute<Integer>(Integer.class);
        }

        if(attribute!=null){
            attribute.setValueCommitMode(valueCommitMode);
            return attribute;
        }

        return null;
    }

    void setValueCommitMode(ValueCommitMode valueCommitMode) {
	this.valueCommitMode = valueCommitMode;
    }

    private static class GenericTwoWayTextAttribute<PropertyType> extends
	    AbstractPropertyViewAttribute<EditText, PropertyType> {
	private ValueCommitMode valueCommitMode;

    private Class<PropertyType> propertyTypeClass;

    public GenericTwoWayTextAttribute(Class<PropertyType> clazz) {
       propertyTypeClass=clazz;
    }

        @Override
	protected void valueModelUpdated(PropertyType newValue) {
        if(newValue==null)
            view.setText("");
        else
	        view.setText(String.valueOf(newValue));
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<PropertyType> valueModel) {
	    if (valueCommitMode == ValueCommitMode.ON_FOCUS_LOST) {
		view.setOnFocusChangeListener(new OnFocusChangeListener() {

		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus)
			    updateValueModel(valueModel, view.getText());
		    }
		});
	    } else {
		view.addTextChangedListener(new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
			updateValueModel(valueModel, s);
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    }
		});
	    }
	}

	void setValueCommitMode(ValueCommitMode valueCommitMode) {
	    this.valueCommitMode = valueCommitMode;
	}

	protected void updateValueModel(ValueModel<PropertyType> valueModel, CharSequence charSequence){
        try {
            if(charSequence.length()==0)
            {
                valueModel.setValue(getDefault(propertyTypeClass));
                return;
            }
            if(propertyTypeClass.isAssignableFrom(String.class)){
                valueModel.setValue((PropertyType) charSequence.toString());
                return;
            }
            Method method=propertyTypeClass.getMethod("valueOf",String.class);
            Object result=method.invoke(null,charSequence.toString());
            valueModel.setValue((PropertyType)result);
        } catch (Exception e) {
            Log.e ("ROBOBINDING","Error setting ValueModel",e);
            valueModel.setValue(getDefault(propertyTypeClass));
        }
    }

        private <T> T getDefault(Class<T> type){

            if(WRAPPERS_TO_PRIMITIVES.containsKey(type))
            {
                Class<?> primitiveType=WRAPPERS_TO_PRIMITIVES.get(propertyTypeClass);
                return (T) Defaults.defaultValue(primitiveType);
            }
            return (T) Defaults.defaultValue(propertyTypeClass);
        }

    private static final Map<Class<?>, Class<?>> WRAPPERS_TO_PRIMITIVES
            = new ImmutableMap.Builder<Class<?>, Class<?>>()
            .put(Boolean.class, boolean.class)
            .put(Byte.class, byte.class)
            .put(Character.class, char.class)
            .put(Double.class, double.class)
            .put(Float.class, float.class)
            .put(Integer.class, int.class)
            .put(Long.class, long.class)
            .put(Short.class, short.class)
            .put(Void.class, void.class)
            .build();
    }

}
