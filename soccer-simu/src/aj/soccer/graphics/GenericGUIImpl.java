package aj.soccer.graphics;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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

}
