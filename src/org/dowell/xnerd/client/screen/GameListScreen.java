package org.dowell.xnerd.client.screen;

import org.dowell.xnerd.client.controller.ContentContainer;
import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

/**
 * This is a dumb screen that will list (with a pager) all
 * the given games. The calling controller must decide the
 * functionality (via the injected Context) within the screen.
 * 
 * @author Matt
 * 
 */
public class GameListScreen {

	/**
	 * This is a public interface that define's this class's dependencies.
	 * Any caller must implement this interface and inject it in to this 
	 * class.
	 * 
	 * @author mjdowell
	 *
	 */
	public interface Context {
		ListDataProvider<Game> getDataProvider();

		boolean shouldShowVoteButton();
		
		boolean shouldShowMarkOwnedButton();

		boolean shouldShowTotalVotes();

		int getRowsPerPage();

		int getTotalRows();
		
		void onClickVoteButton(Game inGame);
		
		void onClickMarkOwnedButton(Game inGame);
		
		String getTitleText();
	}

	private Context myContext;
	private VerticalPanel mainPanel = null;
	private CellTable<Game> gamesTable = null;
	private SimplePager myPager = null;

	public GameListScreen(Context inCtx) {
		myContext = inCtx;
	}

	/**
	 * Loop through all the users and Game them in a table
	 * 
	 * @param inUsers
	 */
	public void showAllGames() {

		mainPanel = new VerticalPanel();

		Label title = new Label(myContext.getTitleText());
		title.setStyleName("title");
		
		// Add the title
		mainPanel.add(title);
		
		// Build the table
		gamesTable = buildCellTable();
		mainPanel.add(gamesTable);

		// Build the pager
		myPager = buildPager(gamesTable);
		mainPanel.add(myPager);

		// Set the content
		ContentContainer.setContent(mainPanel);
	}

	/**
	 * Create a Pager to control the table.
	 * 
	 * @param inTable
	 * @return
	 */
	private SimplePager buildPager(CellTable<Game> inTable) {
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(inTable);
		pager.setPageSize(myContext.getRowsPerPage());
		return pager;
	}

	/**
	 * Build a pageable table. The incoming ListDataProvider is just
	 * a wrapper around a List
	 * 
	 * @return
	 */
	private CellTable<Game> buildCellTable() {
		CellTable<Game> table = new CellTable<Game>();

		// Create name/title column.
		TextColumn<Game> nameColumn = new TextColumn<Game>() {
			@Override
			public String getValue(Game inGame) {
				return inGame.getTitle();
			}
		};

		// Do we want to show the total votes ?
		TextColumn<Game> totalVotesCol = null;
		if (myContext.shouldShowTotalVotes()) {
			// Create Total votes column.
			totalVotesCol = new TextColumn<Game>() {
				@Override
				public String getValue(Game inGame) {
					return String.valueOf(inGame.getVoteCount());
				}
			};
		}

		// Do we want to show the "vote now button" ?
		Column<Game, String> voteNowCol = null;
		if (myContext.shouldShowVoteButton()) {
			ButtonCell voteNowButton = new ButtonCell();
			voteNowCol = new Column<Game, String>(voteNowButton) {
				public String getValue(Game object) {
					return "Vote";
				}
			};

			voteNowCol.setFieldUpdater(new FieldUpdater<Game, String>() {

				@Override
				public void update(int index, Game Game, String value) {
					myContext.onClickVoteButton(Game);
				}
			});
		}
		
		// Do we want to show the "mark as owned button" ?
		Column<Game, String> markOwnedCol = null;
		if (myContext.shouldShowMarkOwnedButton()) {
			
			ButtonCell markOwnedButton = new ButtonCell();
			markOwnedCol = new Column<Game, String>(markOwnedButton) {
				public String getValue(Game object) {
					return "Mark As Owned";
				}
			};

			markOwnedCol.setFieldUpdater(new FieldUpdater<Game, String>() {

				@Override
				public void update(int index, Game Game, String value) {
					myContext.onClickMarkOwnedButton(Game);
				}
			});			
		}

		// Add the columns.
		table.addColumn(nameColumn, "Title");

		if (myContext.shouldShowTotalVotes()) {
			table.addColumn(totalVotesCol, "Total Votes");
		}

		if (myContext.shouldShowVoteButton()) {
			table.addColumn(voteNowCol, "Vote");
		}
		
		if (myContext.shouldShowMarkOwnedButton()) {
			table.addColumn(markOwnedCol, "We bought it!");
		}

		// Set the total row count. This isn't strictly necessary, but it affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		table.setRowCount(myContext.getTotalRows(), true);

		// Push the data into the widget.
		table.setRowData(0, myContext.getDataProvider().getList());

		myContext.getDataProvider().addDataDisplay(table);

		return table;
	}
}
