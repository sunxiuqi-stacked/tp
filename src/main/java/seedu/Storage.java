package seedu;

import seedu.command.Command;
import seedu.exception.EmptyArgumentException;
import seedu.exception.InvalidScoreException;
import seedu.exception.ModuleDoesNotExistException;
import seedu.exception.ModuleNotExistsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;


/**
 * Class handles data storage for DueQuest.
 * It is designed as Singleton.
 */
public class Storage {
    public static final String DEFAULT_FILE_PATH = "data";
    private static Storage storage = null;
    private File directory;
    private File additionalFile;
    private boolean isImport;

    /**.
     * Create a constructor with specific directory path
     *
     * @param directoryPath the path of storage
     */
    private Storage(String directoryPath) {
        this.directory = new File(directoryPath);
        this.isImport = true;

        if (!this.directory.isDirectory()) {
            this.directory.mkdirs();
        }

        additionalFile = new File(this.directory.getAbsolutePath() + "/additional.txt");
        if (!additionalFile.exists()) {
            try {
                additionalFile.createNewFile();
            } catch (IOException ioException) {
                Ui.printAddtionalTxtCreationError();
            }
        }
    }

    public void loadData(ScheduleManager scheduleManager, ModuleManager moduleManager,
                         Ui ui) {
        Ui.printFileLoadingMessage();
        File[] files = this.directory.listFiles();
        try {
            for (File moduleFile : files) {
                if (moduleFile.getName().contains("additional.txt")) {
                    continue;
                }
                processFile(scheduleManager, moduleManager, ui, moduleFile);
            }
        } catch (NullPointerException e) {
            // no file in dir, so just ignore the iteration
        }
        processFile(scheduleManager, moduleManager, ui, this.additionalFile);
        Ui.printFileLoadedMessage();
        this.isImport = false;
    }

    private void processFile(ScheduleManager scheduleManager, ModuleManager moduleManager, Ui ui, File moduleFile) {
        try {
            Scanner scanner = new Scanner(moduleFile);
            while (scanner.hasNextLine()) {
                Command command = Parser.parse(scanner.nextLine());
                if (command != null) {
                    command.execute(scheduleManager, moduleManager, ui);
                }
            }
        } catch (FileNotFoundException e) {
            Ui.printFileMissingError();
        } catch (ModuleDoesNotExistException e) {
            Ui.printModuleDoesNotExistMessage();
        } catch (EmptyArgumentException e) {
            Ui.printEmptyArgumentMessage();
        } catch (InvalidScoreException e) {
            Ui.printInvalidScoreErrorMessage();
        } catch (DueQuestException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Exports the modules and tasks to the file.
     *
     * @param moduleManager moduleManager of Duke
     */
    public void exportData(ModuleManager moduleManager, String moduleCode) {
        if (isImport == true) {
            return; // avoid duplicate export
        }
        File moduleFile = new File(this.directory.getAbsolutePath() + '/' + moduleCode + ".txt");
        try {
            if (!moduleFile.exists()) {
                moduleFile.createNewFile();
            }

            FileWriter moduleWriter = new FileWriter(moduleFile, false);
            moduleWriter.write(moduleManager.getModule(moduleCode).export());
            moduleWriter.close();
        } catch (IOException e) {
            Ui.printFileWritingError();
        } catch (ModuleNotExistsException e) {
            Ui.printModuleMissingWritingError();
        }
    }

    public void exportData(ModuleManager moduleManager) {
        if (isImport == true) {
            return;
        }
        for (String moduleCode : moduleManager.getListOfModuleCodes()) {
            exportData(moduleManager, moduleCode);
        }
    }

    public void exportAdditionalData(String command) {
        if (isImport == true) {
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(this.additionalFile, true);
            fileWriter.write(command + '\n');
            fileWriter.close();
        } catch (IOException e) {
            Ui.printAddtionalTxtWritingError();
        }

    }

    /**.
     * TODO: test empty directoryPath
     *
     * @param directoryPath the path of storage directory
     * @return newly created storage object
     */
    public static Storage setUpStorage(String directoryPath) {
        if (Storage.storage == null) {
            storage = new Storage(Objects.requireNonNullElse(directoryPath, DEFAULT_FILE_PATH));
            return storage;
        } else {
            return Storage.storage;
        }
    }

    public static Storage getStorage() {
        return storage;
    }
}
