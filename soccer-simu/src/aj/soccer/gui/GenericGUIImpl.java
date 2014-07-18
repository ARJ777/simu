package aj.soccer.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

/**
 * Provides generic access to GUI-based functionality.
 */
public class GenericGUIImpl implements GenericGUI {

	/** The main GUI frame. */
	protected final JFrame frame = new JFrame();

	@Override
	public <T> /*@Nullable*/ T selectOne(String title, List<T> selections) {
		JList<Object> list = new JList<>(selections.toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JOptionPane pane = new JOptionPane(new JScrollPane(list), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = pane.createDialog(frame, title);
		dialog.setVisible(true);
		Object ret = pane.getValue();
		if (ret instanceof Integer && ((int) ret) == JOptionPane.OK_OPTION) {
			int selection = list.getSelectedIndex();
			if (selection >= 0) return selections.get(selection);
		}
		return null;
	}

	@Override
	public <T> int[] selectMany(String title, List<T> selections, int[] selected) {
		JList<Object> list = new JList<>(selections.toArray());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndices(selected);
		JOptionPane pane = new JOptionPane(new JScrollPane(list), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = pane.createDialog(frame, title);
		dialog.setVisible(true);
		Object ret = pane.getValue();
		if (ret instanceof Integer && ((int) ret) == JOptionPane.OK_OPTION) {
			return list.getSelectedIndices();
		}
		return null;
	}

	@Override
	public void displayError(String title, String message) {
		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setText(message);
		JOptionPane.showMessageDialog(frame, new JScrollPane(text), title, JOptionPane.ERROR_MESSAGE);
	}

}
