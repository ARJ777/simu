/* XXX: santas-ft specific - DO NOT overwrite when merging from santas! */
package aj.soccer.graphics;

import au.edu.satac.business.codedtypes.SATACSideCodedType;
import au.edu.satac.business.codedtypes.TaskStatusCodedType;
import au.edu.satac.business.manager.DBManager.ServerType;
import au.edu.satac.business.manager.VOException;
import au.edu.satac.business.manager.VOManager;
import au.edu.satac.business.profile.ProfileClassRegistry;
import au.edu.satac.business.profile.ProfilerFactory;
import au.edu.satac.business.utilities.ErrorPrintStream;
import au.edu.satac.business.utilities.SANTASBatchConfig;
import au.edu.satac.business.utilities.SATACLogger;
import au.edu.satac.business.utilities.SLogger;
import au.edu.satac.business.utilities.ValidDateFormatters;
import au.edu.satac.common.vo.santas.MessageVO;
import au.edu.satac.common.vo.santas.OfferCycleVO;
import au.edu.satac.common.vo.santas.ScholarshipCycleVO;
import au.edu.satac.common.vo.santas.TaskVO;
import au.edu.satac.dialogs.KillBatchProcessesDialog;
import au.edu.satac.dialogs.ProgressReporterThread;
import au.edu.satac.dialogs.SaveChangesDialog;
import au.edu.satac.dialogs.TipOftheDayPanel;
import au.edu.satac.scrollabledesktop.JScrollableDesktopPane;
import au.edu.satac.ui.selection.generators.FrmPreferenceStatusEquivalent;
import au.edu.satac.ui.selection.generators.FrmSelectionElementGenerators;
import au.edu.satac.ui.selection.generators.FrmValidPreferenceStatus;
import au.edu.satac.ui.selection.profiling.FrmOfferingProfiles;
import au.edu.satac.updater.ApplicationUpdater;
import au.edu.satac.updater.MainStatusPanelThread;
import au.edu.satac.utilities.AlwaysOnControlMenuItem;
import au.edu.satac.utilities.ApplicantMenuItem;
import au.edu.satac.utilities.CommonUtils;
import au.edu.satac.utilities.ControlMenuItem;
import au.edu.satac.utilities.ControlToolButton;
import au.edu.satac.utilities.CycleChangeEvent;
import au.edu.satac.utilities.CycleChangeListener;
import au.edu.satac.utilities.CycleControl;
import au.edu.satac.utilities.DesktopControl;
import au.edu.satac.utilities.InternalSecurityControl;
import au.edu.satac.utilities.MainControl;
import au.edu.satac.utilities.OfferCycleList;
import au.edu.satac.utilities.OfferCycleMenuItem;
import au.edu.satac.utilities.PersistedProperties;
import au.edu.satac.utilities.SATACHelp;
import au.edu.satac.utilities.SATACIcon;
import au.edu.satac.utilities.SComboBoxModel;
import au.edu.satac.utilities.SMenuItem;
import au.edu.satac.utilities.TransmitLogsToITS;
import au.edu.satac.utilities.VersionControl;
import com.qoppa.pdfViewer.PDFViewerBean;
import daomedge.abstractdao.utilities.CacheFactory;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class TmpMainFrame extends JFrame implements ActionListener, WindowListener, MouseListener {


    private final JMenuBar formMenuBar = new JMenuBar();
    private JTextField messageField;
    private JTextField serverField;
    private JTextField cycleField;
    private JTextField taskCountField;
    private JTextField userField;
    private JTextField capsLockField;
    private JTextField numLockField;
    private JTextField scrollLockField;
    private JTextField memoryField;
    private JTextField timeField;
    private JTextField updateField;
    private JTextField questionsField;
    final JColorChooser colorChooser = new JColorChooser();

    private static final KeyStroke KS_SAVE_ALL = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK, true);
    private static final KeyStroke KS_TASKS = KeyStroke.getKeyStroke(KeyEvent.VK_F2, KeyEvent.CTRL_MASK, true);
    private static final KeyStroke KS_CREATE_APP = KeyStroke.getKeyStroke(KeyEvent.VK_F8, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK, true);
    private static final KeyStroke KS_APP = KeyStroke.getKeyStroke(KeyEvent.VK_F8, KeyEvent.CTRL_MASK, true);
    private static final KeyStroke KS_CREATE_SCHOL_APP = KeyStroke.getKeyStroke(KeyEvent.VK_F12, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK, true);
    private static final KeyStroke KS_SCHOL_APP = KeyStroke.getKeyStroke(KeyEvent.VK_F12, KeyEvent.CTRL_MASK, true);
    public static final KeyStroke KS_PRINT_QUEUE = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK, true);
    public static final KeyStroke KS_FIND_APPLICANT = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK, true);
    private static final String TOGGLE_CYCLE = "toggleCycles";
    public static final KeyStroke KS_TOGGLE_CYCLE = KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.CTRL_MASK, true);
    /**
     * Array of control menu items, one for each type of function.
     */
    private final ControlMenuItem[] assessorMenuItems = new ControlMenuItem[]{
        new ControlMenuItem("My Tasks", FrmTasks.class, new InternalSecurityControl("MyTask"), KS_TASKS),
        new ControlMenuItem("Create Applicant", FrmNewApplicant.class, UTI_SIDE, new InternalSecurityControl("Applicants"), KS_CREATE_APP),
        new ControlMenuItem("Applicant", FrmApplicant.class, UTI_SIDE, new InternalSecurityControl("Applicants"), KS_APP),
        new ControlMenuItem("Pending Logins", FrmPendingLogins.class, new InternalSecurityControl("Logins")),
        new ControlMenuItem("Create Scholarship Applicant", FrmNewApplicant.class, S_SIDE, new InternalSecurityControl("ScholarshipApp"), KS_CREATE_SCHOL_APP),
        new ControlMenuItem("Scholarship Applicant", FrmApplicant.class, S_SIDE, new InternalSecurityControl("ScholarshipApp"), KS_SCHOL_APP),
        new ControlMenuItem("Find Applicant", FrmFindApplicant.class, new InternalSecurityControl("FindApplicants"), KS_FIND_APPLICANT),
        new ControlMenuItem("Corro Receipting", FrmCorroReceipting.class, new InternalSecurityControl("Corro")),
        new ControlMenuItem("Arts Qualification", FrmArtsQualification.class, new InternalSecurityControl("ScholarshipApp")),
        new ControlMenuItem("Load Arts", FrmApplicantUsage.class, new InternalSecurityControl("Applicants")),
        new ControlMenuItem("Receipt Fees", FrmFeePayment.class, new InternalSecurityControl("ReceiptFees")),
        new ControlMenuItem("Applicant Viewing", FrmApplicantUsage.class, new InternalSecurityControl("Applicants")),
        new ControlMenuItem("Reset Applicant Password", FrmChangeApplicantPassword.class, new InternalSecurityControl("Password")),
        new ControlMenuItem("Year 12 Index Search", FrmYear12IndexSearch.class, new InternalSecurityControl("year12Search")),
        new ControlMenuItem("Exam Candidate Search", FrmExamSearch.class, new InternalSecurityControl("examSearch")),
        new ControlMenuItem("XML Lookup", FrmXMLDisp.class, new InternalSecurityControl("xmlDisp")),
        new ControlMenuItem("Applicant's Question", FrmApplicantMessage.class, new InternalSecurityControl("Applicants'Questions")),
        new ControlMenuItem("Load Applicants", FrmOpenApplicants.class, new InternalSecurityControl("Applicants"))
    };
    private final ControlMenuItem[] referenceMenuItems = new ControlMenuItem[]{
        new ControlMenuItem("Allocate Tasks", FrmAllocateTasks.class, new InternalSecurityControl("TaskTargets")),
        new ControlMenuItem("Task Targets", FrmTaskTargets.class, new InternalSecurityControl("TaskTargets")),
        new ControlMenuItem("Print Picking List", FrmPickingList.class, new InternalSecurityControl("TaskTargets")),
        new ControlMenuItem("Qualification Types", FrmQualifications.class, new InternalSecurityControl("QualType")),
        new ControlMenuItem("Personal Attributes", FrmPersonalAttribute.class, new InternalSecurityControl("QualType")),
        new ControlMenuItem("Origins", FrmInstitutions.class, new InternalSecurityControl("Origin")),
        new ControlMenuItem("Course Levels", FrmCourseLevel.class, UTI_SIDE, new InternalSecurityControl("Courses")),
        new ControlMenuItem("Course Sections", FrmCourseSection.class, UTI_SIDE, new InternalSecurityControl("Courses")),
        new ControlMenuItem("Courses", FrmCourse.class, UTI_SIDE, new InternalSecurityControl("Courses")),
        new ControlMenuItem("Cycles", FrmCycle.class, new InternalSecurityControl("Cycles")),
        new ControlMenuItem("Subjects", FrmSubjects.class, new InternalSecurityControl("Subjects")),
        new ControlMenuItem("Fields of Education", FrmFieldOfEducation.class, new InternalSecurityControl("foe")),
        new ControlMenuItem("Flag Reference", FrmFlagReference.class, new InternalSecurityControl("FlagRef")),
        new ControlMenuItem("Security", FrmUserGroups.class, new InternalSecurityControl("Security")),
        new ControlMenuItem("Exams", FrmExams.class, UTI_SIDE, new InternalSecurityControl("STATInfo")),
        new ControlMenuItem("Scholarships", FrmScholarships.class, S_SIDE, new InternalSecurityControl("Scholarships")),
        new ControlMenuItem("Web Menus", FrmWebMenuItems.class, new InternalSecurityControl("WebMenus")),
        new ControlMenuItem("Web Form Checks", FrmWebFormChecks.class, new InternalSecurityControl("WebMenus")),
        new ControlMenuItem("Email Lists", FrmEmailLists.class, new InternalSecurityControl("EmailList")),
        new ControlMenuItem("Team Meetings", FrmTeamMeetings.class, new InternalSecurityControl("Meetings")),
        new ControlMenuItem("Correspondence", FrmCorroType.class, new InternalSecurityControl("CorroTypes")),
        new ControlMenuItem("Correspondence Types", FrmCorrespondence.class, new InternalSecurityControl("CorroTypes")),
        new ControlMenuItem("Arts Course Types", FrmArtsCourseType.class, new InternalSecurityControl("ArtsCourseTypes")),
        new ControlMenuItem("Guide Headers", FrmGuideHeaders.class, new InternalSecurityControl("GuideHeaders")),
        new ControlMenuItem("SANTAS Configuration", FrmSantasConfig.class, new InternalSecurityControl("SantasConfig")),
        new ControlMenuItem("International Configuration", FrmInternationalConfig.class, I_SIDE, new InternalSecurityControl("SantasConfig")),
        new ControlMenuItem("International Agents", FrmInternationalAgent.class, I_SIDE, new InternalSecurityControl("IntAgents")),
        new ControlMenuItem("Year 12 Agents", FrmYear12Agent.class, U_SIDE, new InternalSecurityControl("Yr12Agents")),
        new ControlMenuItem("Edit Warning Messages", FrmWarningMessages.class, new InternalSecurityControl("ErrMess")),
        new ControlMenuItem("Audited Tables", FrmAuditedTable.class, new InternalSecurityControl("AuditTable")),
        new ControlMenuItem("Cycle Parameters", FrmCycleParameters.class, new InternalSecurityControl("CycleParams")),
        new ControlMenuItem("Articulation Courses", FrmArticulation.class, I_SIDE, new InternalSecurityControl("Articulation")),
        new ControlMenuItem("OASys Databases", FrmOasysaDatabase.class, new InternalSecurityControl("OASysDB")),
        new ControlMenuItem("Status Service Messages", FrmStatusServiceMessages.class, U_SIDE, new InternalSecurityControl("StatServMess")),
        new ControlMenuItem("EMail Hold Subjects", FrmEmailSubjectHold.class, new InternalSecurityControl("EmailHold"))
    };
    private final ControlMenuItem[] processMenuItems = new ControlMenuItem[]{
        new ControlMenuItem("New Cycle", FrmNewCycle.class, new InternalSecurityControl("Cycles")),
        new ControlMenuItem("New Qualification Result Type", FrmNewQualResultType.class, new InternalSecurityControl("QualType")),
        new ControlMenuItem("Offer Round", null, UTI_SIDE, new InternalSecurityControl("OfferRound")),
        new ControlMenuItem("Jeeves' Processes", FrmBatchControl.class, new InternalSecurityControl("BatchSched")),
        new ControlMenuItem("Send Process Request to Jeeves", FrmBatchProcess.class, new InternalSecurityControl("BatchControl")),
        new ControlMenuItem("Reports", FrmReport.class, new InternalSecurityControl("ReportRunner")),
        new ControlMenuItem("Charts", FrmCharts.class, new InternalSecurityControl("Charting")),
        new AlwaysOnControlMenuItem("View Logs", FrmLogDisplay.class, new InternalSecurityControl("LogDisp"))
    //new ControlMenuItem("Delete An Applicant", FrmDestroyApplicant.class, new InternalSecurityControl("Destroy")),
    //new ControlMenuItem("Delete A Cycle", FrmDestroyCycle.class, new InternalSecurityControl("Destroy"))
    };
    private final ControlMenuItem[] bulkChangesMenuItems = new ControlMenuItem[]{
        new ControlMenuItem("Course Profile", FrmCourseProfile.class, UT_SIDE, new InternalSecurityControl("CourseProfile")),
        new ControlMenuItem("Course Texts", null, UTI_SIDE, new InternalSecurityControl("Courses")),
        new ControlMenuItem("Course Grouping", null, T_SIDE, new InternalSecurityControl("Courses")),
        new ControlMenuItem("Scholarship Profile", FrmScholarshipProfile.class, S_SIDE, new InternalSecurityControl("ScholProfile")),
        new ControlMenuItem("Qualification Mapping", null, new InternalSecurityControl("QualControl")),
        new ControlMenuItem("STAT Results", null, new InternalSecurityControl("STATRes")),
        new ControlMenuItem("Filing Number Allocation", FrmFilingNumberAllocation.class, new InternalSecurityControl("allocFiling")),
        new ControlMenuItem("Centrelink Takeon", FrmCentrelinkWizard.class, S_SIDE, new InternalSecurityControl("Centrelink")),
        new ControlMenuItem("Inset CoursesRef (XLS)", FrmCourseRef.class, S_SIDE, new InternalSecurityControl("ScholarshipApp")),
        new ControlMenuItem("CDU Offer Upload(XLS)", FrmCDUOfferUpload.class, S_SIDE, new InternalSecurityControl("ScholarshipApp")),
        new ControlMenuItem("Load Year12 to Santas", FrmYear12Load.class, UTI_SIDE, new InternalSecurityControl("Applicants")),
        new ControlMenuItem("Archive Load", FrmArchiveUpload.class, UTI_SIDE, new InternalSecurityControl("Applicants")),
        new ControlMenuItem("SA Archive Precheck", FrmSAArchivePrecheck.class, UTI_SIDE, new InternalSecurityControl("Applicants")),
        new ControlMenuItem("Bulk Mail", FrmBulkMail.class, S_SIDE, new InternalSecurityControl("ScholarshipApp")),
        new ControlMenuItem("Import Export", FrmImportExport.class, new InternalSecurityControl("ImportExport")),
        new ControlMenuItem("Batch Add Notes", FrmBatchNotes.class, new InternalSecurityControl("Applicants")),
        new ControlMenuItem("Create Fee Waiver Vouchers", FrmFeeVouchers.class, I_SIDE, new InternalSecurityControl("FeeWaiver")),
        new ControlMenuItem("Bulk change offering key dates", FrmCourseKeyDate.class, UTI_SIDE, new InternalSecurityControl("CourseDates")),
        new ControlMenuItem("Bulk change offering flags", FrmCourseOfferingFlags.class, UTI_SIDE, new InternalSecurityControl("CourseFlags")),
        //      new ControlMenuItem("Reschedule qualifications", FrmRescheduleQuals.class, new InternalSecurityControl("qualresched")),
        new ControlMenuItem("SQL Editor", FrmSQLEditor.class, new InternalSecurityControl("rawsql"))
    };
    private final ControlMenuItem[] liaisonMenuItems = new ControlMenuItem[]{
        new ControlMenuItem("School", FrmLiaisonSchool.class, new InternalSecurityControl("LiaisonSchool")),
        new ControlMenuItem("Member Institutions", FrmLiaisonMemberInst.class, new InternalSecurityControl("LiaisonMemInst")),
        new ControlMenuItem("Other Institutions", FrmLiaisonOthInst.class, new InternalSecurityControl("LiaisonOthInst")),
        new ControlMenuItem("Organisations", FrmLiaisonOrg.class, new InternalSecurityControl("LiaisonOrg")),
        new ControlMenuItem("Contact Roles", FrmLiaisonContactRole.class, new InternalSecurityControl("LiaisonRoles")),
        new ControlMenuItem("Publications", FrmLiaisonPubs.class, new InternalSecurityControl("LiaisonPubs"))
    };
    private final ControlMenuItem[] tfm_tasksMenuItems = new ControlMenuItem[]{
        new ControlMenuItem("Task Management", FrmAssessorTasksView.class, new InternalSecurityControl("assessor")),
        new ControlMenuItem("My Task Flows", FrmAssessorTaskFlow.class, new InternalSecurityControl("assessor"), KS_TASKS),
        new ControlMenuItem("Task Flow Configuration", FrmTaskFlowConfiguration.class, new InternalSecurityControl("TaskFlowConfig"))
    };
    private final ControlToolButton[] toolButtons = new ControlToolButton[]{
        new ControlToolButton("Find App.", SATACIcon.FIND_APP_ICON.getImageIcon(), FrmFindApplicant.class, null, new InternalSecurityControl("FindApplicants")),
        new ControlToolButton("Courses", SATACIcon.COURSES.getImageIcon(), FrmCourse.class, UTI_SIDE, new InternalSecurityControl("Courses")),
        new ControlToolButton("Cycles", SATACIcon.CYCLES.getImageIcon(), FrmCycle.class, null, new InternalSecurityControl("Cycles")), // new ControlToolButton("Shortcut Keys", SATACIcon.SHORTCUTKEYS.getImageIcon(), FrmPDFShortcutKeys.class, null, new InternalSecurityControl(""))
    };
    private final JMenu mnuFile = new JMenu(" File");
    private final JMenu mnuAssessor = new JMenu("Assessor");
    private final JMenu mnuReference = new JMenu("Set Up");
    private final JMenu mnuProcesses = new JMenu("Processes");
    private final JMenu mnuBulkChanges = new JMenu("Bulk Changes");
    private final JMenu mnuLiaison = new JMenu("Liaison");
    private final JMenu mnuContext = new JMenu(CONTEXT_MENU_CAPTION);
    private final JMenu mnuHelp = new JMenu("Help");
    private final JMenu mnuPrevApps = new JMenu("Previous Applicants");
    private final JMenu tfm_mnuTasks = new JMenu("Tasks flows");
    private final SMenuItem mnuFile_ChangePassword = new SMenuItem("Change password");
    private final SMenuItem mnuFile_Refresh = new SMenuItem("Refresh Caches");
    private final SMenuItem mnuFile_Save = new SMenuItem("Save All");
    private final SMenuItem mnuFile_Update = new SMenuItem("Check For Updates");
    private final SMenuItem mnuFile_Options = new SMenuItem("Options");
    private final SMenuItem mnuFile_ClearAppSearch = new SMenuItem("Clear Applicant Search list");
    private final SMenuItem mnuFile_SendLogsToITS = new SMenuItem("Send Logs to ITS");
    private final SMenuItem mnuFile_Exit = new SMenuItem("Exit");
    private final SMenuItem mnuHelp_Contents = new SMenuItem("Contents");
    private final SMenuItem mnuHelp_Context = new SMenuItem("Not available");
    private final SMenuItem mnuHelp_About = new SMenuItem("About");
    private final ControlMenuItem[] ft_mnuSelections = new ControlMenuItem[]{
        new ControlMenuItem("Result Types", FrmResultType.class, new InternalSecurityControl("resulttype")),
        new ControlMenuItem("Conversion Tables", FrmConversion.class, new InternalSecurityControl("conversion")),
        new ControlMenuItem("Conversion Table Maps", FrmConversionMap.class, new InternalSecurityControl("conversion")),
        new ControlMenuItem("Basis Of Match", FrmBasisOfMatchTableType.class, new InternalSecurityControl("Basisofmatch")),
        new ControlMenuItem("Tips of the Day", FrmTipOfTheDay.class, new InternalSecurityControl("Tips"))
    };
    //private final SMenuItem mnuSelection = new SMenuItem("Selection Machinery", new InternalSecurityControl("assessors"), SecurityControl.PERMISSION_READ_WRITE);
    private final JMenu mnuSelection = new JMenu("Selection Machinery");
    private final ControlMenuItem[] mnuSelectionItems = new ControlMenuItem[]{
        new ControlMenuItem("Selection Element Generators", FrmSelectionElementGenerators.class, new InternalSecurityControl("assessors")),
        new ControlMenuItem("Offering Profiles", FrmOfferingProfiles.class, new InternalSecurityControl("assessors"))
    };
    private final ControlMenuItem[] mnuPrefStatusItems = new ControlMenuItem[]{
            new ControlMenuItem("Status Value Equivalents", FrmPreferenceStatusEquivalent.class, new InternalSecurityControl("assessors")),
            new ControlMenuItem("Valid Preference Status", FrmValidPreferenceStatus.class, new InternalSecurityControl("assessors"))
     };

    private List<OfferCycleMenuItem> offerCycleMenus;

    private TmpMainFrame() {
        /*
         * XXX CAUTION: Circular dependency here - DesktopControl must NOT be held in a field
         * or called by a constructor method in any class instantiated in SATACMain!
         */
        desktopControl = DesktopControl.newInstance(this, databaseControl.getVOManager(), databaseControl.getLoginUser(), databaseControl.getServerName());
        desktop = new JScrollableDesktopPane();
    }

    private static SATACMain newInstance() {
        if (mainFrame != null) {
            throw new IllegalStateException("Attempting to create duplicate instance of singleton");
        }
        options = PersistedProperties.getInstance();
        databaseControl = MainControl.getInstance();
        cycleControl = CycleControl.newInstance(databaseControl.getVOManager(), databaseControl.getLoginUser(), databaseControl.getServerName());
        mainFrame = new SATACMain();
        return mainFrame;
    }

    /**
     * add listeners specific to the SATACMain frame.
     */
    protected void addListeners() {
        cycleControl.addCycleChangeListener(this);
        mnuFile_Refresh.addActionListener(this);
        mnuFile_ChangePassword.addActionListener(this);
        mnuFile_Save.addActionListener(this);
        mnuFile_Options.addActionListener(this);
        mnuFile_ClearAppSearch.addActionListener(this);
        mnuFile_Update.addActionListener(this);
        mnuFile_Exit.addActionListener(this);
        mnuHelp_Contents.addActionListener(this);
        mnuHelp_Context.setEnabled(false);
        mnuHelp_Context.addActionListener(this);
        mnuHelp_About.addActionListener(this);
        mnuFile_SendLogsToITS.addActionListener(this);
        addWindowListener(this);
        getRootPane().registerKeyboardAction(this, TOGGLE_CYCLE, KS_TOGGLE_CYCLE, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        desktopControl.setMainFrameSize();
        options.savePanelTypes();
        if (closeActions()) {
            exitSystem();
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) {
            if (e.getSource() == updateField && waitToUpdate()) {
                updateApplication();
            } else if (e.getSource() == taskCountField) {
                ControlMenuItem tm = new ControlMenuItem("", FrmTasks.class, new InternalSecurityControl("MyTask"));
                tm.menuClicked();
            } else if (e.getSource() == questionsField) {
                ControlMenuItem tm = new ControlMenuItem("", FrmApplicantMessage.class, new InternalSecurityControl("Applicants'Questions"));
                tm.menuClicked();
            } else if (e.getSource() == serverField) {
                ServerType dbType = databaseControl.getServerType();
                if (dbType == ServerType.Test) {
                    Color c = JColorChooser.showDialog(
                            this,
                            "Choose Background Color",
                            Color.CYAN);
                    if (c != null) {
                        options.setProperty(COLOUR_OF_TEST_SERVER_STATUSBAR, Integer.toString(c.getRGB()));
                        serverField.setBackground(c);
                    } else {
                        serverField.setBackground(Color.CYAN);
                    }
                } else if (dbType == ServerType.Sandbox) {
                    Color c = JColorChooser.showDialog(
                            this,
                            "Choose Background Color",
                            Color.CYAN);
                    if (c != null) {
                        options.setProperty(COLOUR_OF_STATUSBAR, Integer.toString(c.getRGB()));
                        serverField.setBackground(c);
                    } else {
                        serverField.setBackground(Color.CYAN);
                    }
                } else {
                    serverField.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void updateApplication() {
        exitSystem();
    }

    public void resetContextMenu() {
        mnuContext.setText(CONTEXT_MENU_CAPTION);
        mnuContext.setEnabled(false);
        mnuContext.removeAll();
    }

    public void setContextMenu(String title, JMenuItem[] menuItems) {
        mnuContext.setText(title);
        mnuContext.setEnabled(true);
        mnuContext.removeAll();
        for (JMenuItem menuItem : menuItems) {
            mnuContext.add(menuItem);
        }
    }

    public void setHelpMenu(String helpAreaName) {
        if (helpAreaName != null && helpAreaName.trim().length() != 0) {
            mnuHelp_Context.setText(helpAreaName);
            mnuHelp_Context.setEnabled(true);
        } else {
            mnuHelp_Context.setText("Not available");
            mnuHelp_Context.setEnabled(false);
        }
    }

    /**
     * Saves all of the pending changes on all of the frames.
     */
    private void saveChanges() {
        for (JInternalFrame frm : desktop.getAllFrames()) {
            if (frm instanceof FrmSATAC) {
                FrmSATAC frame = (FrmSATAC) frm;
                frame.saveChanges();
            }
        }
    }

    private boolean closeActions() {
        // Check against all the currently opened frames
        // To see if any of them have unsaved data.
        boolean hasChanges = false;
        for (JInternalFrame frm : desktop.getAllFrames()) {
            if (frm instanceof FrmSATAC) {
                FrmSATAC frame = (FrmSATAC) frm;
                hasChanges |= frame.hasChanges();
            }
        }

        if (hasChanges) {
            int closeAction = SaveChangesDialog.dispSaveMessage();
            if (closeAction == SaveChangesDialog.SAVE_DIALOG_CANCEL) {
                return false;
            }
            if (closeAction == SaveChangesDialog.SAVE_DIALOG_SAVE) {
                saveChanges();
            }
        }
        int batchProcessCount = ProgressReporterThread.getBatchProcessCount();
        if (batchProcessCount != 0) {
            if (KillBatchProcessesDialog.showKillDialog() != KillBatchProcessesDialog.STOP_BATCHES) {
                return false;
            }
        }

        for (JInternalFrame frm : desktop.getAllFrames()) {
            if (frm instanceof FrmSATAC) {
                FrmSATAC frame = (FrmSATAC) frm;
                frame.closeActions();
            }
        }
        return true;
    }

    /**
     * System exit process. closes the dao connection(if it was opened) and
     * saves everything. It also checks the staging area to see if there is an
     * update available and copies the files as needed.
     */
    private void exitSystem() {
        logger.info("Closing down application...");
        clearTempFiles();
        MainControl.destroyInstance();
        if (statusThread != null) {
            statusThread.stopRunning();
        }
        dispose();
        logger.info("Closed down application!");
        System.exit(0);
    }

    public void clearTempFiles() {
        File localDir = new File(System.getProperty("user.dir"));
        File[] files = localDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("tmp_");
            }
        });
        for (File file : files) {
            file.deleteOnExit();
        }
    }

    /**
     * Responds to the help|about menu event.
     */
    public void dispAboutBox() {
        HelpAboutPanel.dispHelpAbout(getMainCaption());
    }

    private void clearImageCache() {
        File homeDir = MainControl.getUserHomeDirectory();
        File localDir = new File(homeDir, "renderedImages");
        File[] files = localDir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    /**
     * Action performed callback.
     *
     * @param e The event triggered by the action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mnuHelp_Contents) {
            SATACHelp.showHelp();
        } else if (e.getSource() == mnuHelp_Context) {
            SATACHelp.showHelp("main." + mnuHelp_Context.getText() + ".index");
        } else if (e.getSource() == mnuFile_Save) {
            saveChanges();
        } else if (e.getSource() == mnuFile_Update) {
            if (updater != null && updater.updatesAvailable()) {
                if (waitToUpdate()) {
                    updateApplication();
                }
            } else {
                ErrorMessagePanel.dispMessage("Updater", "No updates available", "Updater");
            }
        } else if (e.getSource() == mnuFile_Refresh) {
            ProfilerFactory.clearCaches();
            CacheFactory.flushCache();
            OfferCycleList.clearInstance();
            SComboBoxModel.initialiseModels();
            desktopControl.resetActiveOfferCycles();
            clearImageCache();
            SANTASBatchConfig.getConfig(databaseControl.getServerName());
        } else if (e.getSource() == mnuFile_ChangePassword) {
            PanelSATACLogin.changePassword(databaseControl.getLoginUser());
        } else if (e.getSource() == mnuFile_Exit) {
            windowClosing(null);
        } else if (e.getSource() == mnuHelp_About) {
            dispAboutBox();
        } else if (e.getSource() == mnuFile_Options) {
            showOptions();
        } else if (e.getSource() == mnuFile_ClearAppSearch) {
            options.clearApplicantSearches();
        } else if (e.getSource() == mnuFile_SendLogsToITS) {
            TransmitLogsToITS.transmitLogs(this);
        } else if (e.getActionCommand().equalsIgnoreCase(TOGGLE_CYCLE)) {
            cycleControl.toggleCycle();
            for (OfferCycleMenuItem menu : offerCycleMenus) {
                if (menu.getCycleId().equals(cycleControl.getCycleId())) {
                    menu.setSelected(true);
                }
            }
        }
    }

    private void showOptions() {
        PersistedProperties options = PersistedProperties.getInstance();
        int res = JOptionPane.showConfirmDialog(null, options.getPanel(), "Options",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            options.setProperties();
        }
    }

    public void setMainTitle() {
        StringBuilder buf = new StringBuilder();
        buf.append(MAIN_CAPTION);
        buf.append(" (");
        buf.append(VersionControl.getVersion());
        if (versionText != null) {
            buf.append(" ");
            buf.append(versionText);
        } else if (isDevMode()) {
            buf.append(" DEV");
        }
        buf.append(")");
        setTitle(buf.toString());
    }

    private JTextField buildStatusPanelPart(int size, String initValue) {
        JTextField res = new JTextField(size);
        res.setText(initValue);
        res.setBackground(Color.LIGHT_GRAY);
        res.setEditable(false);
        return res;
    }

    public void setMessage(String message) {
        messageField.setText(message);
    }

    @Override
    public void cycleChanged(CycleChangeEvent e) {
        if (e.isOfferCycle()) {
            cycleField.setText(e.getOfferCycle().getOfferCycleName());
            setDefaultCycleColor(desktopControl.getCycleColor(e.getOfferCycle().getSatacSide(), e.getOfferCycle().getOfferCycleColour()));
        } else if (e.isScholarshipCycle()) {
            cycleField.setText(e.getScholarshipCycle().getScholarshipCycleName());
            setDefaultCycleColor(desktopControl.getCycleColor(SATACSideCodedType.SATAC_SCHOLARSHIP_SIDE, e.getScholarshipCycle().getScholarshipCycleColour()));
        } else {
            cycleField.setText("");
            setDefaultCycleColor(Color.WHITE);
        }
        changeMediumStatuses();
    }

    public void setFrameCycleColor(Color col) {
    }

    public void setDefaultCycleColor(Color col) {
        desktop.setToolBarColor(col);
        cycleField.setBackground(col);
    }

    public void changeQuickStatuses() {
        if (desktopControl.isWindows()) {
            if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
                capsLockField.setText("cap");
            } else {
                capsLockField.setText("");
            }
            if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK)) {
                numLockField.setText("num");
            } else {
                numLockField.setText("");
            }
            if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_SCROLL_LOCK)) {
                scrollLockField.setText("scr");
            } else {
                scrollLockField.setText("");
            }
        } else {
            capsLockField.setText("");
            numLockField.setText("");
            scrollLockField.setText("");
        }
        Runtime runtime = Runtime.getRuntime();
        memoryField.setText((runtime.totalMemory() / MEGA_BYTE) + "M of " + (runtime.maxMemory() / MEGA_BYTE) + "M");
        Date date = new Date();
        timeField.setText(ValidDateFormatters.formatDate(ValidDateFormatters.DF_TIME, date));
        timeField.setToolTipText(ValidDateFormatters.formatDate(ValidDateFormatters.DF, date));
    }

    public void changeMediumStatuses() {
        VOManager voManager = databaseControl.getVOManager();
        String userId = voManager.getUserId();
        TaskVO task = new TaskVO();
        task.setSatacUser(userId);
        task.setOfferCycleId(cycleControl.getCycleId());
        task.setTaskStatus(TaskStatusCodedType.STATUS_ALLOCATED);
        MessageVO message = new MessageVO();
        message.setAnswered("N");
        try {
            List<Object> tasks = voManager.selectVOs(task);
            if (tasks.isEmpty()) {
                taskCountField.setText("no tasks");
                taskCountField.setToolTipText("no tasks for " + userId + " for " + cycleControl.getCycleCode());
            } else {
                taskCountField.setText(tasks.size() + " tasks");
                taskCountField.setToolTipText(tasks.size() + " tasks for " + userId + " for " + cycleControl.getCycleCode());
            }
        } catch (VOException ex) {
            taskCountField.setText("???");
            taskCountField.setToolTipText(ex.getMessage());
        }
        try {
            List<Object> messages = voManager.selectVOs(message);
            if (messages.isEmpty()) {
                questionsField.setText("no messages");
                questionsField.setToolTipText("no messages unanswered for " + cycleControl.getCycleCode());
            } else {
                questionsField.setText(messages.size() + " messages");
                questionsField.setToolTipText(messages.size() + " messages unanswered for " + cycleControl.getCycleCode());
            }
        } catch (VOException ex) {
            questionsField.setText("???");
            questionsField.setToolTipText(ex.getMessage());
        }
        serverField.setText(databaseControl.getServerDescription().toUpperCase()
                + " (" + databaseControl.getServerName().toUpperCase() + ")");
        ServerType dbType = databaseControl.getServerType();
        if (dbType == ServerType.Test) {
            serverField.setToolTipText("Pick your colour");
            String userColour = options.getProperty(COLOUR_OF_TEST_SERVER_STATUSBAR);
            if (userColour == null) {
                serverField.setBackground(Color.CYAN);
            } else {
                serverField.setBackground(new Color(Integer.parseInt(userColour)));
            }
        } else if (dbType == ServerType.Sandbox) {
            serverField.setToolTipText("Pick your colour");
            String userColour = options.getProperty(COLOUR_OF_STATUSBAR);
            if (userColour == null) {
                serverField.setBackground(Color.CYAN);
            } else {
                serverField.setBackground(new Color(Integer.parseInt(userColour)));
            }
        } else {
            serverField.setBackground(Color.LIGHT_GRAY);
        }
    }

    public void changeSlowStatuses() {
        if (CommonUtils.isOutsideLoginPeriod()) {
            int batchProcessCount = ProgressReporterThread.getBatchProcessCount();
            if (batchProcessCount == 0) {
                logger.info("Shutting sysem down to do the late hour.");
                windowClosing(null);
            } else {
                logger.info("Note shutting sysem down to do the late hour as there are " + batchProcessCount + " batch processes running.");
            }
        }
        if (updater != null && updater.updatesAvailable()) {
            updateField.setBackground(Color.RED);
            updateField.setText("New");
            updateField.setToolTipText("A new version is available");
            updateField.addMouseListener(this);
        } else {
            updateField.setBackground(Color.LIGHT_GRAY);
            updateField.setText("");
            updateField.setToolTipText(null);
            updateField.removeMouseListener(this);
        }
    }

    private JPanel buildToolBar() {
        JPanel tools = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.BOTH;
        for (ControlToolButton button : toolButtons) {
            tools.add(button, gbc);
            gbc.gridy++;
        }

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        JPanel positional = new JPanel(new GridBagLayout());
        positional.add(tools, gbc2);

        return positional;
    }

    private Box buildStatusPanel() {
        Box box = Box.createHorizontalBox();
        messageField = buildStatusPanelPart(40, ""); // TODO Check login user is/is not fast-track.
//        serverField = buildStatusPanelPart(20, control.getDatabaseServer().getServerDescription().toUpperCase()
//                + " (" + control.getDatabaseServer().getServerName().toUpperCase() + ")");
        serverField = buildStatusPanelPart(20, "????");
        cycleField = buildStatusPanelPart(30, "Cycle");
        taskCountField = buildStatusPanelPart(10, "????");
        questionsField = buildStatusPanelPart(10, "????");

        userField = buildStatusPanelPart(10, databaseControl.getLoginUserId());
        capsLockField = buildStatusPanelPart(3, "cap");
        numLockField = buildStatusPanelPart(3, "num");
        scrollLockField = buildStatusPanelPart(3, "scr");
        memoryField = buildStatusPanelPart(12, "");
        timeField = buildStatusPanelPart(10, "99:99");
        updateField = buildStatusPanelPart(2, "!");

        taskCountField.addMouseListener(this);
        questionsField.addMouseListener(this);
        serverField.addMouseListener(this);

        CycleChangeEvent e = new CycleChangeEvent();
        e.setOfferCycle(cycleControl.getActiveOfferCycle());
        e.setScholarshipCycle(cycleControl.getActiveScholarshipCycle());
        cycleChanged(e);
        //  databaseChanged();
        changeQuickStatuses();
        changeMediumStatuses();
        changeSlowStatuses();

        box.add(messageField);
        box.add(Box.createHorizontalGlue());
        box.add(serverField);
        box.add(Box.createHorizontalGlue());
        box.add(cycleField);
        box.add(Box.createHorizontalGlue());
        box.add(taskCountField);
        box.add(Box.createHorizontalGlue());
        box.add(questionsField);
        box.add(Box.createHorizontalGlue());
        box.add(userField);
        box.add(Box.createHorizontalGlue());
        box.add(capsLockField);
        box.add(Box.createHorizontalGlue());
        box.add(numLockField);
        box.add(Box.createHorizontalGlue());
        box.add(scrollLockField);
        box.add(Box.createHorizontalGlue());
        box.add(memoryField);
        box.add(Box.createHorizontalGlue());
        box.add(timeField);
        box.add(Box.createHorizontalGlue());
        box.add(updateField);
        return box;
    }

    public void setFileMenu() {
        mnuFile.removeAll();
        mnuFile_Save.setAccelerator(KS_SAVE_ALL);
        mnuFile.add(mnuFile_Save);
        mnuFile.addSeparator();
        ButtonGroup offerCycleMenuGroup = new ButtonGroup();
        offerCycleMenus = new ArrayList<>();
        int cycleNumber = 0;
        for (OfferCycleVO offerCycle : cycleControl.getActiveOfferCycles()) {
            if (desktopControl.canUserSeeCycle(offerCycle)) {
                OfferCycleMenuItem menu = new OfferCycleMenuItem(offerCycle, offerCycleMenuGroup, cycleNumber++);
                menu.setSelected(offerCycle.getOfferCycleId().equals(cycleControl.getCycleId()));
                mnuFile.add(menu);
                offerCycleMenus.add(menu);
            }
        }

        for (ScholarshipCycleVO scholarshipCycle : cycleControl.getActiveScholarshipCycles()) {
            if (cycleControl.canUserSeeCycle(scholarshipCycle)) {
                OfferCycleMenuItem menu = new OfferCycleMenuItem(scholarshipCycle, offerCycleMenuGroup, cycleNumber++);
                menu.setSelected(scholarshipCycle.getScholarshipCycleId().equals(cycleControl.getCycleId()));
                mnuFile.add(menu);
                offerCycleMenus.add(menu);
            }
        }

        mnuFile.add(mnuPrevApps);
        setPreviousAppsMenu();
        mnuFile.addSeparator();
        mnuFile.add(mnuFile_Options);
        mnuFile.add(mnuFile_ClearAppSearch);
        mnuFile.add(mnuFile_Update);
        if (!MainControl.getInstance().isFTUser()) {
            mnuFile.add(mnuFile_ChangePassword);
        }
        mnuFile.add(mnuFile_Refresh);
        mnuFile.add(mnuFile_SendLogsToITS);
        mnuFile.add(mnuFile_Exit);
    }

    public void setPreviousAppsMenu() {
        mnuPrevApps.removeAll();
        List<String> appIds = new ArrayList<>(desktopControl.getPreviousApplicants());
        for (String appId : appIds) {
            ApplicantMenuItem appMenu = new ApplicantMenuItem(appId, databaseControl.getVOManager());
            if (appMenu.isOK()) {
                mnuPrevApps.add(appMenu);
            }
        }
    }

    /**
     * Initialises the frame
     */
    private void initialiseGUI() {
        setLayout(new BorderLayout());

        // To adjust the menubar position
//
//        mnuFile.setMargin(new Insets(1, 0, 1, 0));
//        mnuAssessor.setMargin(new Insets(1, 0, 1, 0));
//        mnuReference.setMargin(new Insets(1, 0, 1, 0));
//        mnuProcesses.setMargin(new Insets(1, 0, 1, 0));
//        mnuBulkChanges.setMargin(new Insets(1, 0, 1, 0));
//        mnuLiaison.setMargin(new Insets(1, 0, 1, 0));
//        mnuHelp.setMargin(new Insets(1, 0, 1, 0));
//        mnuContext.setMargin(new Insets(1, 0, 1, 0));
//        ft_mnuRoles.setMargin(new Insets(1, 0, 1, 0));
//        tfm_mnuTasks.setMargin(new Insets(1, 0, 1, 0));
        mnuAssessor.setVisible(false);
        mnuReference.setVisible(false);
        mnuProcesses.setVisible(false);
        mnuBulkChanges.setVisible(false);
        mnuLiaison.setVisible(false);

        for (ControlMenuItem menu : assessorMenuItems) {
            mnuAssessor.add(menu);
            mnuAssessor.setVisible(mnuAssessor.isVisible() || menu.isVisible());
        }

        for (ControlMenuItem menu : referenceMenuItems) {
            mnuReference.add(menu);
            mnuReference.setVisible(mnuReference.isVisible() || menu.isVisible());
        }

        for (ControlMenuItem menu : processMenuItems) {
            mnuProcesses.add(menu);
            mnuProcesses.setVisible(mnuProcesses.isVisible() || menu.isVisible());
        }

        for (ControlMenuItem menu : bulkChangesMenuItems) {
            mnuBulkChanges.add(menu);
            mnuBulkChanges.setVisible(mnuBulkChanges.isVisible() || menu.isVisible());
        }

        for (ControlMenuItem menu : liaisonMenuItems) {
            mnuLiaison.add(menu);
            mnuLiaison.setVisible(mnuLiaison.isVisible() || menu.isVisible());
        }

        setFileMenu();
        setMainTitle();

        cycleControl.fireCycleChange();

        mnuHelp.add(mnuHelp_Contents);
        mnuHelp.add(mnuHelp_Context);
        mnuHelp.addSeparator();
        mnuHelp.add(mnuHelp_About);

        formMenuBar.add(mnuFile);
        formMenuBar.add(mnuAssessor);
        formMenuBar.add(mnuReference);
        formMenuBar.add(mnuProcesses);
        formMenuBar.add(mnuBulkChanges);
        formMenuBar.add(mnuLiaison);
        formMenuBar.add(mnuContext);
        resetContextMenu();
        formMenuBar.add(mnuHelp);



        if (databaseControl.isFTUser()) {
            //************ Add Fast-Track-only menus here *************
            mnuBulkChanges.add(new ControlMenuItem("Load Post-codes", FrmPostcodeUpload.class, UTI_SIDE, new InternalSecurityControl("QualControl")));
            for (ControlMenuItem menu : tfm_tasksMenuItems) {
                tfm_mnuTasks.add(menu);
            }

            formMenuBar.add(tfm_mnuTasks);
            for (ControlMenuItem menu : ft_mnuSelections) {
                mnuReference.add(menu);
                mnuReference.setVisible(mnuReference.isVisible() || menu.isVisible());
            }
            mnuReference.add(mnuSelection);
            for (ControlMenuItem item : mnuSelectionItems) {
                mnuSelection.add(item);
            }
            mnuSelection.addSeparator();
            for (ControlMenuItem item : mnuPrefStatusItems) {
                mnuSelection.add(item);
            }
        }
        getContentPane().add(desktop, BorderLayout.CENTER);
        getContentPane().add(buildStatusPanel(), BorderLayout.SOUTH);
        String toolBarPosn = options.getToolBarPlacement();
        // For new users always default toolbar at "West" - options include 'west', 'east', 'none'
        if (toolBarPosn == null || toolBarPosn.isEmpty()) {
            toolBarPosn = "west";
        }
        if (toolBarPosn.equalsIgnoreCase("west")) {
            getContentPane().add(buildToolBar(), BorderLayout.WEST);
        } else if (toolBarPosn.equalsIgnoreCase("east")) {
            getContentPane().add(buildToolBar(), BorderLayout.EAST);
        }
        this.setJMenuBar(formMenuBar);
        desktop.registerMenuBar(formMenuBar);
        addListeners();
        setIconImage(SATACIcon.SATAC_ICON.getImage());
        pack();
    }

    private void startStatus() {
        statusThread = new MainStatusPanelThread(this);
        statusThread.start();
    }

    /**
     * Gets the main caption for this frame.
     *
     * @return the main title part.
     */
    public String getMainCaption() {
        return MAIN_CAPTION;
    }

    public JScrollableDesktopPane getDesktop() {
        return desktop;
    }
    private static SecondSplashScreen secondSplashScreen;

    private static void dispLoadingProgress(String caption, int percentComp) {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null && secondSplashScreen == null) {
            try {
                secondSplashScreen = new SecondSplashScreen(splash.getImageURL(), splash.getBounds());
            } catch (IOException ex) {
                logger.info("IO exception thrown building splas screen", ex);
            }
        }

        if (splash != null && splash.isVisible()) {
            Graphics2D g = splash.createGraphics();
            Dimension size = splash.getSize();
            Rectangle2D r = g.getFontMetrics().getStringBounds(caption, g);
            int fontWidth = (int) r.getWidth();
            int fontHeight = (int) r.getHeight();
            int x = 10;
            int y = size.height - (fontHeight * 3);
            int w = size.width - 30;
            int h = (fontHeight * 2);
            int fontx = ((w - fontWidth) / 2) + x;
            int fonty = y + fontHeight + 2;
            int progy = fonty + 8;
            g.setComposite(AlphaComposite.Clear);
            g.setPaintMode();
            g.setColor(new Color(120, 190, 32));
            g.fillRect(x, y, w, h);
            g.setColor(Color.WHITE);
            g.drawString(caption, fontx, fonty);
            g.setColor(Color.RED);
            g.fillRect(x, progy, (w * percentComp) / 100, 2);
            splash.update();
            if (percentComp >= 100) {
                splash.close();
            }
        } else if (secondSplashScreen != null) {
            secondSplashScreen.setVisible(true);
            secondSplashScreen.dispLoadingProgress(caption, percentComp);
            if (percentComp >= 100) {
                secondSplashScreen.setVisible(false);
                secondSplashScreen.dispose();
                secondSplashScreen = null;
            }
        }
    }

    private static boolean waitToUpdate() {
        int instanceCount = getInstanceCount();
        JCheckBox chkReallyNot = new JCheckBox("There really isn't");
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(new JLabel("The system needs to be updated."), BorderLayout.NORTH);
        if (instanceCount > 1) {
            JPanel instances = new JPanel(new BorderLayout());
            StringBuilder buf = new StringBuilder();
            buf.append("<html>");
            String cr = Integer.toHexString(instances.getBackground().getRed());
            String cg = Integer.toHexString(instances.getBackground().getGreen());
            String cb = Integer.toHexString(instances.getBackground().getBlue());
            buf.append("<body bgcolor=\"");
            buf.append(cr);
            buf.append(cg);
            buf.append(cb);
            buf.append("\">");
            buf.append("There seems to be <b>");
            buf.append(instanceCount);
            buf.append("</b> copies still running.<br />");
            buf.append("Please ensure there are no other ");
            buf.append("copies of SANTAS running before continuing.</body></html>");
            JEditorPane count = new JEditorPane("text/html", buf.toString());
            instances.add(count, BorderLayout.CENTER);
            instances.add(chkReallyNot, BorderLayout.SOUTH);
            pnl.add(instances, BorderLayout.CENTER);
        }
        pnl.add(new JLabel("Do you wish to continue?"), BorderLayout.SOUTH);
        int resp = JOptionPane.showConfirmDialog(null,
                pnl,
                "Update",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (chkReallyNot.isSelected()) {
            clearInstances();
        }
        return resp == JOptionPane.YES_OPTION;
    }
    private static File instanceFile;

    private static void createInstanceFile() {
        try {
            Date start = new Date();
            String instanceFileName = ".instance_" + ValidDateFormatters.formatDate(ValidDateFormatters.DF_FILENAME, start);
            instanceFile = new File(userDirectory, instanceFileName);
            instanceFile.createNewFile();
            instanceFile.deleteOnExit();
        } catch (IOException ex) {
        }
    }

    private static int getInstanceCount() {
        File[] instanceFiles = userDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(".instance");
            }
        });
        return instanceFiles == null ? 0 : instanceFiles.length;
    }

    private static void clearInstances() {
        File[] instanceFiles = userDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(".instance");
            }
        });
        if (instanceFiles != null) {
            for (File f : instanceFiles) {
                f.delete();
            }
        }
    }

    /**
     * THE public static void main method. This is the entry point for the whole
     * SANTAS system. It starts here, gets the default user id(the last login)
     * and displays the login dialog box. Checks the returned value from the
     * login box(true or false) If all is well displays this frame.
     *
     * @param args The string arguments for the application.
     */
    public static void main(String[] args) {
        SATACLogger.setIsSANTAS();
        checkStartup();
        interpretArguments(args);
        checkForUpdate();
        setLookAndFeel();
        createInstanceFile();

        // Order is important here
        dispLoadingProgress("Initialising Help", 10);
        SATACHelp.getInstance();
        dispLoadingProgress("Logging in", 20);
        if (PanelSATACLogin.login()) {
            SATACMain instance = newInstance();
            desktopControl.adjustFonts();
            try {
                dispLoadingProgress("Initialising Batch Communications", 30);
                SANTASBatchConfig.getConfig(databaseControl.getServerName());
                dispLoadingProgress("Initialising Models", 40);
                SComboBoxModel.initialiseModels();
                dispLoadingProgress("Initialising Active Cycle lists", 50);
                cycleControl.readActiveCycle();
                dispLoadingProgress("Loading Profilers", 60);
                ProfileClassRegistry.getInstance(databaseControl.getLoginUser(), databaseControl.getDAO());
                dispLoadingProgress("Initialising Profiler Factory", 90);
                ProfilerFactory.setInstance(databaseControl.getVOManager());
                dispLoadingProgress("Setting PDFView Key", 95);
                PDFViewerBean.setKey("521TEIMCSUG7CUGIP3TAEOLLSR");
                logger.info("PDFVeiwer version = " + PDFViewerBean.getVersion());
                dispLoadingProgress("Starting", 100);
                instance.initialiseGUI();
                instance.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                instance.setBounds(desktopControl.getMainFrameSize());
                instance.startStatus();
                instance.setVisible(true);

                if (databaseControl.getProperties().isShowTips()) {
                    TipOftheDayPanel tip = new TipOftheDayPanel();
                    tip.setFocusable(true);
                    tip.requestFocusInWindow();
                }

                if (databaseControl.getProperties().isShowTasks()) {
                    ControlMenuItem tm = new ControlMenuItem("", FrmTasks.class, new InternalSecurityControl("MyTask"));
                    tm.menuClicked();
                }
                if (databaseControl.getProperties().isShowApplicants()) {
                    ControlMenuItem tm = null;
                    if (cycleControl.getActiveOfferCycle() != null) {
                        tm = new ControlMenuItem("", FrmApplicant.class, new InternalSecurityControl("Applicants"));
                    }
                    if (cycleControl.getActiveScholarshipCycle() != null) {
                        tm = new ControlMenuItem("", FrmApplicant.class, new InternalSecurityControl("ScholarshipApp"));
                    }
                    if (tm != null) {
                        tm.menuClicked();
                    }
                }
            } catch (Exception ex) {
                logger.fatal("Error registering new modules", ex);
                dispLoadingProgress("Shutting down", 100);
            }
        } else {
            dispLoadingProgress("Shutting down", 100);
        }
    }

    public static SATACMain getMainFrame() {
        return mainFrame;
    }

    private static void setLookAndFeel() {
        // use Nimbus look and feel. If Nimbus is not available then use system look and feel
        try {
            System.setErr(new ErrorPrintStream(true, VersionControl.getVersion()));
            boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
            if (isWindows) {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (FileNotFoundException | ClassNotFoundException | UnsupportedEncodingException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            logger.fatal("Error setting up environment", ex);
            System.exit(1);
        }
    }
    private static void checkForUpdate() {
        if (updatePath != null) {
            updater = new ApplicationUpdater(updatePath);
            if (updater.updatesAvailable()) {
                if (waitToUpdate()) {
                    dispLoadingProgress("Updating to a Newer Version", 0);
                    updater.updateFiles();
                    updater.restartApplication();
                }
                System.exit(0);
            }
        }
    }
    private static void interpretArguments(String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--test")/*TODO Remove this*/ || arg.equalsIgnoreCase("--dev")) {
                logger.info("Running SATAC in DEVELOPMENT mode!");
                ErrorPrintStream.setInTest(true);
                setDevMode(true);
            }
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-u")) {
                updatePath = args[++i];
            }
        }
    }
    // Sree
//    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
//        Object[] objs
//                = javax.swing.UIManager.getLookAndFeel().
//                getDefaults().keySet().toArray();
//
//        for (Object obj : objs) {
//            if (obj.toString().startsWith("InternalFrame")) {
//                javax.swing.UIManager.getDefaults().get(obj);
//            }
//        }
//
//        UIManager.getDefaults().put("defaultFont", f);
//        for (Map.Entry<Object, Object> entry : javax.swing.UIManager.getDefaults().entrySet()) {
//            Object key = entry.getKey();
//            Object value = javax.swing.UIManager.get(key);
//            if (value instanceof javax.swing.plaf.FontUIResource) {
//                javax.swing.UIManager.put(key, f);
//            }
//        }
//
//        UIManager.put("Button.font", f);
//        UIManager.put("ToggleButton.font", f);
//        UIManager.put("RadioButton.font", f);
//        UIManager.put("CheckBox.font", f);
//        UIManager.put("ColorChooser.font", f);
//        UIManager.put("ComboBox.font", f);
//        UIManager.put("Label.font", f);
//        UIManager.put("List.font", f);
//        UIManager.put("MenuBar.font", f);
//        UIManager.put("MenuItem.font", f);
//        UIManager.put("RadioButtonMenuItem.font", f);
//        UIManager.put("CheckBoxMenuItem.font", f);
//        UIManager.put("Menu.font", f);
//        UIManager.put("PopupMenu.font", f);
//        UIManager.put("OptionPane.font", f);
//        UIManager.put("Panel.font", f);
//        UIManager.put("ProgressBar.font", f);
//        UIManager.put("ScrollPane.font", f);
//        UIManager.put("Viewport.font", f);
//        UIManager.put("TabbedPane.font", f);
//        UIManager.put("Table.font", f);
//        UIManager.put("TableHeader.font", f);
//        UIManager.put("TextField.font", f);
//        UIManager.put("PasswordField.font", f);
//        UIManager.put("TextArea.font", f);
//        UIManager.put("TextPane.font", f);
//        UIManager.put("EditorPane.font", f);
//        UIManager.put("TitledBorder.font", f);
//        UIManager.put("ToolBar.font", f);
//        UIManager.put("ToolTip.font", f);
//        UIManager.put("Tree.font", f);
//
//        objs = javax.swing.UIManager.getLookAndFeel().
//                getDefaults().keySet().toArray();
//
//        for (Object obj : objs) {
//            if (obj.toString().startsWith("InternalFrame")) {
//                javax.swing.UIManager.getDefaults().get(obj);
//            }
//        }
//
//    }
    // end
    private static void checkStartup() {
        if (CommonUtils.isOutsideLoginPeriod()) {
            System.exit(1);
        }
        String userDir = System.getProperty("user.dir");
        logger.info("Starting SANTAS version " + au.edu.satac.utilities.VersionControl.getVersion()
                + " from " + userDir);
        if (userDir.toLowerCase().startsWith("z:")) {
            logger.info("Illegal execution of staging version, run from local drive only.");
            ErrorMessagePanel.dispPlainMessage("Illegal execution of staging version, run from local drive only.");
            System.exit(1);
        }
    }

}
