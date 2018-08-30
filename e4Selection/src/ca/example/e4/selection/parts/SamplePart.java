package ca.example.e4.selection.parts;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ca.example.e4.selection.model.Album;
import ca.example.e4.selection.model.Song;

public class SamplePart {

	private static final String ALBUM_MENU_ID = "e4selection.popupmenu.albummenu";
	
	private TableViewer tableViewer;
	private TreeViewer 	treeViewer;
	private Label 		currentSelection;
	
	@Inject EMenuService menuSerivce;
	@Inject ESelectionService selectionService;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		treeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.BORDER );
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		menuSerivce.registerContextMenu(treeViewer.getControl(), ALBUM_MENU_ID);
		treeViewer.setContentProvider( new TreeNodeContentProvider() );
		treeViewer.setLabelProvider( new LabelProvider() {
			@Override
			public String getText(Object element) {
				Album album = ((Album)(((TreeNode)element).getValue()));
				return album.getName() + " (" + album.isOpenable() +  ")";
			}
		});
		treeViewer.addSelectionChangedListener( sce -> {
			TreeNode node = (TreeNode)sce.getStructuredSelection().getFirstElement();
			Album selectedAlbum = (Album)node.getValue();
			selectionService.setSelection(selectedAlbum);
		});
		treeViewer.setAutoExpandLevel( TreeViewer.ALL_LEVELS );
		treeViewer.setInput( createInitialAlbumModel() );
		
		
		tableViewer = new TableViewer(parent);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setLabelProvider( new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Song)element).getName();
			}			
		});
		tableViewer.addSelectionChangedListener( sce -> {
			Song selectedSong = (Song)sce.getStructuredSelection().getFirstElement();
			selectionService.setSelection(selectedSong);
		});
		tableViewer.setInput(createInitialSongModel());

		currentSelection = new Label(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(currentSelection);
		currentSelection.setText("Selected Class: ");
	}

	@Inject
	@Optional
	public void updateSelectionLabel( @Named(IServiceConstants.ACTIVE_SELECTION) Object selection ) {
		String msg = "Selection: null";
		
		if( currentSelection != null && !currentSelection.isDisposed() ) {
			if( selection == null ) {
				currentSelection.setText(msg);	
			}else {
				msg = "Selection ["+ selection.getClass().getCanonicalName() +"]:" + selection.toString();
				currentSelection.setText( msg );
			}
			System.out.println( selection.toString() );
		}
	}
	
	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	private List<Song> createInitialSongModel() {
		return Arrays.asList(new Song("Song 1"), new Song("Song 2"), new Song("Song 3"), new Song("Song 4"), new Song("Song 5") );
	}
	
	private TreeNode[] createInitialAlbumModel() {
		TreeNode[] nodeList = new TreeNode[1];
		
		// Cheat here so we don't need another model object.
		nodeList[0] = new TreeNode(  new Album("Library", false)  );
		
		nodeList[0].setChildren( new TreeNode[] {
				new TreeNode( new Album("Album 1", true ) ),
				new TreeNode( new Album("Album 2", false ) ),
				new TreeNode( new Album("Album 3", true ) ),
				new TreeNode( new Album("Album 4", false) ),
				new TreeNode( new Album("Album 5", true ) )
		});
		
		return nodeList;
	}
}