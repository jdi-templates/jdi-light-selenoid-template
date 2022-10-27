package org.mytests.uiobjects.example.site.sections;

import com.epam.jdi.light.elements.complex.Combobox;
import com.epam.jdi.light.elements.complex.dropdown.Dropdown;
import com.epam.jdi.light.elements.composite.Form;
import com.epam.jdi.light.elements.interfaces.base.ICoreElement;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.*;
import org.mytests.uiobjects.example.entities.Contacts;

import java.lang.reflect.Field;

import static com.jdiai.tools.ReflectionUtils.isInterface;

public class ContactForm extends Form<Contacts> {
	TextField name, lastName, position, passportNumber, passportSeria;

	Dropdown gender;
	Combobox religion;

	public Checkbox passport, acceptConditions;
	TextArea description;

	@UI("['Submit']") public Button submit;

	@Override
	public void fillAction(Field field, Object element, Object parent, String setValue) {
		if (isInterface(field, TextField.class))
			((ICoreElement)element).core().highlight();
		super.fillAction(field, element, parent, setValue);
	}
}