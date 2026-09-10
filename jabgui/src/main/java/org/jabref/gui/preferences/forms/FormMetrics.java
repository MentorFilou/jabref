package org.jabref.gui.preferences.forms;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableNumberValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import org.jspecify.annotations.NullMarked;

/// The layout measurements a preference tab shares with [PreferencesFormBuilder], so that a
/// tab building part of its view by hand (the `.custom(Node)` hatch) lines up with the parts the
/// builder laid out, rather than re-typing the numbers.
@NullMarked
public final class FormMetrics {

    /// Gap between elements, both between rows and between an element and what is attached to it.
    public static final double GAP = 10.0;

    /// Width of a labelled button in a button row, so that the buttons in one row are uniform.
    public static final double BUTTON_WIDTH = 100.0;
    static final double SHORT_FIELD_WIDTH = 100.0;
    static final double LABEL_COLUMN_MIN_WIDTH = 120.0;

    static final double INFO_LABEL_INDENT = 20.0;

    static final double ICON_BUTTON_SIZE = 20.0;

    /// Row height of a table, as a multiple of the font size.
    private static final double FONT_HEIGHT_MULTIPLIER = 2.5;

    /// Row height used until the font source has a font, i.e. before it is styled.
    private static final double DEFAULT_ROW_HEIGHT = 30.0;

    /// The column header of a table, expressed in rows.
    private static final double HEADER_HEIGHT_ESTIMATE = 1.1;

    private FormMetrics() {
    }

    /// Gives `table` the height of all of its rows, so that it never scrolls within the already
    /// scrolling preferences dialog: a table left at a height of its own clamps its rows into a
    /// second scroll area while the page below it stays empty.
    ///
    /// @param rowCount   the number of rows, as an observable, since the table grows and shrinks with it
    /// @param fontSource a themed control in the same tree, whose font tracks the configured font
    ///                   size; a [TableView] carries no font of its own to read the row height off
    public static void sizeToContent(TableView<?> table, ObservableNumberValue rowCount, Label fontSource) {
        DoubleBinding rowHeight = Bindings.createDoubleBinding(
                () -> fontSource.getFont() != null ? fontSource.getFont().getSize() * FONT_HEIGHT_MULTIPLIER : DEFAULT_ROW_HEIGHT,
                fontSource.fontProperty());
        table.fixedCellSizeProperty().bind(rowHeight);
        table.prefHeightProperty().bind(Bindings.add(rowCount, HEADER_HEIGHT_ESTIMATE).multiply(rowHeight));
    }
}
