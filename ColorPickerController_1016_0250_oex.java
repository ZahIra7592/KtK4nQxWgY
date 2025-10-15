// 代码生成时间: 2025-10-16 02:50:21
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.DynamicForm;
import play.data.Form;
import javax.inject.Inject;
import models.Color;
import views.html.colorPicker;

/**
 * Controller for handling color selection operations.
 */
public class ColorPickerController extends Controller {
    /**
     * The form for color selection.
     */
    private final Form<Color> colorForm;

    /**
     * Inject the form instance.
     * @param colorForm The form for color selection.
     */
    @Inject
    public ColorPickerController(Form<Color> colorForm) {
        this.colorForm = colorForm;
    }

    /**
     * Display the color picker form.
     * @return The rendered color picker view.
     */
    public Result showColorPicker() {
        return ok(colorPicker.render(colorForm));
    }

    /**
     * Handle the form submission and process the selected color.
     * @return Redirects to the color picker form with a success message.
     */
    public Result setColor() {
        DynamicForm form = Form.form().bindFromRequest();
        String colorName = form.field("color").value();
        try {
            // Validate the color name and process it if valid.
            Color selectedColor = Color.valueOf(colorName.toUpperCase());
            flash("success", "Color selected: " + selectedColor);
            return redirect(routes.ColorPickerController.showColorPicker());
        } catch (IllegalArgumentException e) {
            // Handle the case where the color name is not valid.
            flash("error", "Invalid color selection: 