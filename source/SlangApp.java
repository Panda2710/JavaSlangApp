import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;




public class SlangApp {
    private JTextField searchField;
    private JComboBox<String> searchMode;
    private DefaultTableModel dictionaryModel;
    private JTable dictionaryTable;

    private SlangService service;

    private DefaultTableModel historyModel;

    private JComboBox<String> quizMode;
    private JLabel questionLabel;
    private JLabel quizFeedback;
    private final JButton[] answerButtons = new JButton[4];

    private QuizQuestion currentQuiz;

    public void start() {
        SlangFileStore store = new SlangFileStore();

        try {
            // KHOI DONG
            DictionaryData data = store.loadOrCreateData();
            this.service = new SlangService(data);

            System.out.println(
                "So slang: " + data.getDictionary().size()
            );
            createAndShowGUI();


            // TEST DOC FILE TXT RIENG
            // Dung bien rieng de khong thay data service dang dung.
            /*
            DictionaryData textData = store.readTextFile("slang.txt");

            System.out.println(
                "Number of slang from txt: "
                + textData.getDictionary().size()
            );
            */

            // TEST DOC FILE DAT RIENG
            /*
            DictionaryData savedData = store.loadData("dictionary.dat");

            System.out.println(
                "Number of slang from dat: "
                + savedData.getDictionary().size()
            );
            */

            // TEST SEARCH BY SLANG
            // searchBySlang() se ghi them vao history.
           /* 
            System.out.println(
                "Definition of HB: " + service.searchBySlang(" HB ")
            );

            System.out.println(
                service.searchBySlang("MY_TEST_SLANG")
            );
           */

            // TEST SEARCH BY DEFINITION
            /*
            Map<String, List<String>> results =
                    service.searchByDefinition("money");

            if (results.isEmpty()) {
                System.out.println("Khong tim thay.");
            } else {
                for (Map.Entry<String, List<String>> entry
                        : results.entrySet()) {

                    System.out.println(
                        entry.getKey() + " -> " + entry.getValue()
                    );
                }
            }
            */

            // TEST ADD SLANG
            /*
            List<String> meanings = new ArrayList<>();
            meanings.add("My test definition");
            meanings.add("a second definition");

            boolean added =
                    service.addSlang("MY_TEST_SLANG", meanings);

            System.out.println("Them thanh cong: " + added);

            System.out.println(
                service.getMeanings("MY_TEST_SLANG")
            );
            */

            // TEST EDIT SLANG
            /*
            List<String> updatedMeanings = new ArrayList<>();
            updatedMeanings.add("New meaning");

            boolean edited =
                    service.editSlang("MY_TEST_SLANG", updatedMeanings);

            System.out.println("Sua thanh cong: " + edited);

            System.out.println(
                service.getMeanings("MY_TEST_SLANG")
            );
            */

            // TEST DELETE SLANG
            /*
            boolean deleted = service.deleteSlang("MY_TEST_SLANG");

            System.out.println("Xoa thanh cong: " + deleted);

            System.out.println(
                service.getMeanings("MY_TEST_SLANG")
            );

            boolean deletedNonExistent =
                    service.deleteSlang("NON_EXISTENT_SLANG");

            System.out.println(
                "Xoa slang khong ton tai: " + deletedNonExistent
            );
            */

            // TEST RANDOM
            /* 
            String randomSlang = service.getRandomSlang();

            if (randomSlang == null) {
                System.out.println("Tu dien dang rong.");
            } else {
                System.out.println("Slang ngau nhien: " + randomSlang);
                System.out.println(service.getMeanings(randomSlang));
            }
            */

            // TEST RESET DICTIONARY
            // Reset thay toan bo noi dung tu dien trong bo nho.
            /*
            service.deleteSlang("HB");

            System.out.println(
                "Sau khi xoa: " + service.getMeanings("HB")
            );

            service.resetDictionary();

            System.out.println(
                "Sau khi reset: " + service.getMeanings("HB")
            );
            */

            // TEST SAVE DATA TRUC TIEP
            /*
            store.saveData(data, "dictionary.dat");
            System.out.println("Da luu du lieu.");
            */

            // TEST ADD VA SAVE QUA SERVICE
            /* 
            List<String> newMeanings = new ArrayList<>();
            newMeanings.add("My test definition");

            if (service.addSlang("MY_TEST2_SLANG", newMeanings)) {
                service.save();
                System.out.println("Da them va luu.");
            } else {
                System.out.println("Khong them duoc.");
            }
            */

            // TEST KIEM TRA DU LIEU SAU KHI MO LAI APP
            /*
            System.out.println(
                service.getMeanings("MY_TEST_SLANG")
            );
            */

            /* 
            // HIEN THI HISTORY DA DOC TU FILE
            List<String> history = service.getHistory();

            System.out.println("Lich su tim kiem:");

            if (history.isEmpty()) {
                System.out.println("Chua co lich su.");
            } else {
                for (String slang : history) {
                    List<String> meanings = service.getMeanings(slang);

                    if (meanings == null) {
                        System.out.println(
                            slang + " -> Khong co trong tu dien"
                        );
                    } else {
                        System.out.println(
                            slang + " -> " + String.join("; ", meanings)
                        );
                    }
                }
            }

            */


            //TEST APPEND VA SAVE QUA SERVICE
            /* 
            List<String> extraMeanings = new ArrayList<>();
            extraMeanings.add("My additional meaning");

            boolean appended = service.appendMeanings("HB", extraMeanings);

            if (appended) {
                service.save();
                System.out.println("Da bo sung va luu.");
            }

            System.out.println(service.getMeanings("HB"));
            */

            //TEST QUIZ
            /* 
            QuizQuestion quiz = service.createSlangQuiz();

            if (quiz == null) {
                System.out.println("Khong du du lieu de tao cau hoi.");
            } else {
                System.out.println(quiz.getQuestion());

                List<String> answers = quiz.getOptions();

                for (int i = 0; i < answers.size(); i++) {
                    System.out.println((i + 1) + ". " + answers.get(i));
                }

                // Tam gia su nguoi dung chon dap an thu nhat.
                System.out.println("Chon dap an 1: " + quiz.isCorrect(0));
            }
            */

            //TEST QUIZ DEFINITION
            /*

            QuizQuestion definitionQuiz = service.createDefinitionQuiz();

            if (definitionQuiz == null) {
                System.out.println("Khong du du lieu de tao cau hoi.");
            } else {
                System.out.println(definitionQuiz.getQuestion());

                List<String> definitionOptions = definitionQuiz.getOptions();

                for (int i = 0; i < definitionOptions.size(); i++) {
                    System.out.println(
                        (i + 1) + ". " + definitionOptions.get(i)
                    );
                }

                System.out.println(
                    "Chon dap an 1: " + definitionQuiz.isCorrect(0)
                );
            }
            */

            

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Loi doc/ghi du lieu: " + e.getMessage());
        }
    }

    //TAOUI
    private void createAndShowGUI(){
        JFrame frame = new JFrame("slang Dictionary");
        frame.setJMenuBar(createMenuBar(frame));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabs = new JTabbedPane();

        JPanel dictionaryPanel = createDictionaryPanel();

        JPanel historyPanel = createHistoryPanel();

        JPanel quizPanel = createQuizPanel();


        

        tabs.addTab("Dictionary", dictionaryPanel);
        tabs.addTab("History", historyPanel);
        tabs.addTab("Quiz", quizPanel);

        tabs.addChangeListener(e -> {
            if (tabs.getSelectedIndex() == 1) {
                refreshHistory();
            }
        });
        

        frame.add(tabs);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);


    }
    //TAO MENUBAR
    private JMenuBar createMenuBar(JFrame frame){
        JMenuBar menuBar= new JMenuBar();
        JMenu dataMenu = new JMenu("Settings");
        JMenuItem resetItem = new JMenuItem("Reset to Original Dictionary");
        resetItem.setEnabled(true);
        resetItem.addActionListener(e -> showResetDialog());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> frame.dispose());

        dataMenu.add(resetItem);
        dataMenu.addSeparator();
        dataMenu.add(exitItem);

        menuBar.add(dataMenu);
        return menuBar;
    }

    private JPanel createDictionaryPanel(){
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel searchPanel = new JPanel(
        new FlowLayout(FlowLayout.LEFT, 10, 5)
    );

        JLabel searchLabel= new JLabel("keyword:");

        searchField = new JTextField(20);

        searchMode = new JComboBox<>(new String[]{"Slang","Definition"});

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchMode);
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        dictionaryModel = new DefaultTableModel(new String[]{"Slang", "Meanings"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //k edit duoc cell
            }

        };

        dictionaryTable = new JTable(dictionaryModel);
        dictionaryTable.setRowHeight(28);

        dictionaryTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        dictionaryTable.getColumnModel().getColumn(1).setPreferredWidth(600);

        JScrollPane scrollPane = new JScrollPane(dictionaryTable);

        panel.add(scrollPane, BorderLayout.CENTER);
        





        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        JButton addButton= new JButton("Add slang");
        JButton editButton= new JButton("Edit slang");
        JButton delButton= new JButton("Delete slang");
        JButton randButton= new JButton("Random slang");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        randButton.addActionListener(e -> showRandomSlang());
        delButton.addActionListener(e -> showDeleteDialog());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(delButton);
        buttonPanel.add(randButton);

        panel.add(buttonPanel,BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHistoryPanel(){
        JPanel panel= new JPanel(new BorderLayout(10,10));

        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        historyModel = new DefaultTableModel(new String[]{"Slang searched","Definition"},0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        JTable historyTable =new JTable(historyModel);

        historyTable.setRowHeight(28);
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(historyTable);

        panel.add(scrollPane,BorderLayout.CENTER);

        refreshHistory();
    
        return panel;
    }
    private void refreshHistory(){
        historyModel.setRowCount(0);
        
        for(String slang : service.getHistory()){
            List<String> meanings = service.getMeanings(slang);

            String definition;

            if(meanings == null){
                definition = "N/A";
            }else{
                definition = String.join("; ",meanings);
            }

            historyModel.addRow(new Object[]{slang,definition});
        }
    }


    private void performSearch(){
        String keyword = searchField.getText().trim();
        long startTime = System.nanoTime();
        if(keyword.isEmpty()){
            JOptionPane.showMessageDialog(searchField, "Please enter a keyword to search.");
            return;
        }
    

        dictionaryModel.setRowCount(0); // xoa results truoc 

        try{
            if(searchMode.getSelectedIndex()==0){
                List<String> meanings = service.searchBySlang(keyword);

                if(meanings !=null){
                    dictionaryModel.addRow(new Object[]{keyword,String.join(";",meanings)});
                }
            }else{
                Map<String, List<String>> results = service.searchByDefinition(keyword);
                
                results.forEach((slang, meanings) -> {
                    dictionaryModel.addRow(new Object[]{slang,String.join(";",meanings)});
                });
            }

            double elapsedMs = (System.nanoTime() - startTime) / 1_000_000.0;

            System.out.printf(
                "Tim kiem va cap nhat bang: %.2f ms | %d ket qua%n",
                elapsedMs,
                dictionaryModel.getRowCount()
            );

            if(dictionaryModel.getRowCount()==0){
                JOptionPane.showMessageDialog(searchField, "No results found for: " + keyword);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(searchField, "An error occurred while searching."+e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            
        }


    }

    private void showRandomSlang(){
        String slang= service.getRandomSlang();

        if(slang==null){
            JOptionPane.showMessageDialog(dictionaryTable,"empty"); return;
        }
        List<String> meanings = service.getMeanings(slang);

        dictionaryModel.setRowCount(0);
        dictionaryModel.addRow(new Object[]{slang,String.join("; ",meanings)});
    }

    private void showAddDialog() {
        JTextField slangInput = new JTextField(25);
        JTextArea meaningsInput = new JTextArea(6, 25);

        meaningsInput.setLineWrap(true);
        meaningsInput.setWrapStyleWord(true);

        JPanel slangPanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        slangPanel.add(new JLabel("Slang:"));
        slangPanel.add(slangInput);

        JPanel meaningsPanel = new JPanel(new BorderLayout(5, 5));

        meaningsPanel.add(
            new JLabel("Definition: each definition per line"),
            BorderLayout.NORTH
        );

        meaningsPanel.add(
            new JScrollPane(meaningsInput),
            BorderLayout.CENTER
        );

        JPanel form = new JPanel(new BorderLayout(10, 10));
        form.add(slangPanel, BorderLayout.NORTH);
        form.add(meaningsPanel, BorderLayout.CENTER);

        // Lap lai de giu noi dung nhap neu du lieu chua hop le.
        while (true) {
            int choice = JOptionPane.showConfirmDialog(
                dictionaryTable,
                form,
                "Add slang",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (choice != JOptionPane.OK_OPTION) {
                return;
            }

            String slang = slangInput.getText().trim();

            if (slang.isEmpty()
                    || slang.contains("\n")
                    || slang.contains("\r")) {

                JOptionPane.showMessageDialog(
                    form,
                    "empty or wrong input format"
                );
                continue;
            }

            List<String> meanings = new ArrayList<>();

            for (String line : meaningsInput.getText().split("\\R")) {
                String meaning = line.trim();

                if (!meaning.isEmpty()) {
                    meanings.add(meaning);
                }
            }

            if (meanings.isEmpty()) {
                JOptionPane.showMessageDialog(
                    form,
                    "Type atleast 1 definition."
                );
                continue;
            }

            boolean changed;

            if (service.getMeanings(slang) == null) {
                changed = service.addSlang(slang, meanings);
            } else {
                String[] options = {"Overwrite", "Append", "Cancel"};

                int duplicateChoice = JOptionPane.showOptionDialog(
                    dictionaryTable,
                    "Slang exists. what do you want to do",
                    "Existed slang",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]
                );

                if (duplicateChoice == 0) {
                    changed = service.editSlang(slang, meanings);
                } else if (duplicateChoice == 1) {
                    changed = service.appendMeanings(slang, meanings);
                } else {
                    return;
                }
            }

            if (!changed) {
                JOptionPane.showMessageDialog(
                    dictionaryTable,
                    "cant update slang."
                );
                return;
            }

            // Hien thi du lieu vua thay doi trong bo nho.
            dictionaryModel.setRowCount(0);
            dictionaryModel.addRow(new Object[]{
                slang,
                String.join("; ", service.getMeanings(slang))
            });

            try {
                service.save();

                JOptionPane.showMessageDialog(
                    dictionaryTable,
                    "updated and saved"
                );

            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    dictionaryTable,
                    "updated but cant save "
                        + e.getMessage(),
                    "Saving error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

            return;
        }
    }

    private void showEditDialog() {
        String input = JOptionPane.showInputDialog(
            dictionaryTable,
            "type slang to edit "
        );

        if (input == null) {
            return;
        }

        String slang = input.trim();
        if (slang.isEmpty()) {
            JOptionPane.showMessageDialog(dictionaryTable, "type a slang");
            return;
        }
        List<String> currentMeanings = service.getMeanings(slang);

        if (currentMeanings == null) {
            JOptionPane.showMessageDialog(
                dictionaryTable,
                "cant find slang definition."
            );
            return;
        }

        JTextArea meaningsInput = new JTextArea(
            String.join("\n", currentMeanings), 6, 25
        );
        meaningsInput.setLineWrap(true);
        meaningsInput.setWrapStyleWord(true);

        JPanel form = new JPanel(new BorderLayout(5, 5));
        form.add(
            new JLabel("each definition in a line"),
            BorderLayout.NORTH
        );
        form.add(new JScrollPane(meaningsInput), BorderLayout.CENTER);

        while (true) {
            int choice = JOptionPane.showConfirmDialog(
                dictionaryTable,
                form,
                "edit definition of " + slang,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (choice != JOptionPane.OK_OPTION) {
                return;
            }

            List<String> newMeanings = new ArrayList<>();

            for (String line : meaningsInput.getText().split("\\R")) {
                String meaning = line.trim();
                if (!meaning.isEmpty()) {
                    newMeanings.add(meaning);
                }
            }

            if (newMeanings.isEmpty()) {
                JOptionPane.showMessageDialog(
                    dictionaryTable,
                    "type atleast 1 definition"
                );
                continue;
            }

            if (!service.editSlang(slang, newMeanings)) {
                JOptionPane.showMessageDialog(
                    dictionaryTable,
                    "cant edit slang."
                );
                return;
            }

            dictionaryModel.setRowCount(0);
            dictionaryModel.addRow(new Object[]{
                slang,
                String.join("; ", newMeanings)
            });

            try {
                service.save();
                JOptionPane.showMessageDialog(dictionaryTable, "edited and saved");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    dictionaryTable,
                    "edited but cant save "
                        + e.getMessage(),
                    "Saving Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

            return;
        }
    }
    private void showDeleteDialog() {
        String input = JOptionPane.showInputDialog(
            dictionaryTable,
            "enter slang to delete:"
        );

        if (input == null) {
            return;
        }

        String slang = input.trim();

        if (slang.isEmpty()) {
            JOptionPane.showMessageDialog(dictionaryTable, "enter a slang.");
            return;
        }

        if (service.getMeanings(slang) == null) {
            JOptionPane.showMessageDialog(dictionaryTable, "cant find");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            dictionaryTable,
            "delete '" + slang + "' and its definitions?",
            "confirm delete ",
            JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (!service.deleteSlang(slang)) {
            JOptionPane.showMessageDialog(dictionaryTable, "cant delete slang.");
            return;
        }

        // Xoa hang cua slang trong bang ket qua, neu dang hien thi.
        for (int row = dictionaryModel.getRowCount() - 1; row >= 0; row--) {
            if (slang.equals(dictionaryModel.getValueAt(row, 0))) {
                dictionaryModel.removeRow(row);
            }
        }

        try {
            service.save();
            JOptionPane.showMessageDialog(dictionaryTable, "Deleted and saved");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                dictionaryTable,
                "deleted but cant save " + e.getMessage(),
                "saving error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void showResetDialog() {
        int choice = JOptionPane.showConfirmDialog(
            dictionaryTable,
            "restore back to original slang.txt?\n"
                + "edited,added,delete slang options will be discarded",
            "confirm restore",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            service.resetDictionary();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                dictionaryTable,
                "cant reset" + e.getMessage(),
                "reset error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        dictionaryModel.setRowCount(0);
        searchField.setText("");
        refreshHistory();

        try {
            service.save();

            JOptionPane.showMessageDialog(
                dictionaryTable,
                "Restored and saved"
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                dictionaryTable,
                "Restored but cant save "
                    + e.getMessage(),
                "saving error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private JPanel createQuizPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        quizMode = new JComboBox<>(
            new String[]{"Definitions", "Slang"}
        );

        JButton nextButton = new JButton("New quiz");
        nextButton.addActionListener(e -> loadQuizQuestion());
        quizMode.addActionListener(e -> loadQuizQuestion());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controls.add(new JLabel("Mode:"));
        controls.add(quizMode);
        controls.add(nextButton);

        questionLabel = new JLabel("press New quiz to start", SwingConstants.CENTER);
        quizFeedback = new JLabel(" ", SwingConstants.CENTER);

        JPanel answersPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        for (int i = 0; i < answerButtons.length; i++) {
            int answerIndex = i;

            JButton button = new JButton("-");
            button.setEnabled(false);
            button.addActionListener(e -> checkQuizAnswer(answerIndex));

            answerButtons[i] = button;
            answersPanel.add(button);
        }

        JPanel quizBody = new JPanel(new BorderLayout(10, 10));
        quizBody.add(questionLabel, BorderLayout.NORTH);
        quizBody.add(answersPanel, BorderLayout.CENTER);

        panel.add(controls, BorderLayout.NORTH);
        panel.add(quizBody, BorderLayout.CENTER);
        panel.add(quizFeedback, BorderLayout.SOUTH);

        return panel;
    }
    private void loadQuizQuestion() {
        if (quizMode.getSelectedIndex() == 0) {
            currentQuiz = service.createSlangQuiz();
        } else {
            currentQuiz = service.createDefinitionQuiz();
        }

        quizFeedback.setText(" ");

        if (currentQuiz == null) {
            questionLabel.setText("Not enough data to create ");

            for (JButton button : answerButtons) {
                button.setText("-");
                button.setEnabled(false);
            }
            return;
        }

        questionLabel.setText(currentQuiz.getQuestion());
        List<String> options = currentQuiz.getOptions();

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(options.get(i));
            answerButtons[i].setEnabled(true);
        }
    }

    private void checkQuizAnswer(int answerIndex) {
        if (currentQuiz.isCorrect(answerIndex)) {
            quizFeedback.setText("CORRECT!!");
        } else {
            quizFeedback.setText("INCORRECTR!!, press New quiz to try again");
        }

        for (JButton button : answerButtons) {
            button.setEnabled(false);
        }
    }


}


