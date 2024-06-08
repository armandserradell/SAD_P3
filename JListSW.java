
import javax.swing.*;

public class JListSW {
    private JList<String> list;
    private DefaultListModel<String> listModel;

    public JListSW() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
    }

    // Devuelve la instancia de JList
    public JList<String> getJList() {
        return list;
    }

    // Añade un usuario a la lista si no está ya presente
    public void addUser(String user) {
        if (!listModel.contains(user)) {
            listModel.addElement(user);
        }
    }

    // Elimina un usuario de la lista
    public void removeUser(String user) {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).contains(user)) {
                listModel.remove(i);
                break;
            }
        }
    }
}
