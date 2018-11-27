import React from 'react';

import { withStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import ListSubheader from '@material-ui/core/ListSubheader';
import SeriePopover from './SeriePopover'


function GridSeries(props) {
    const { classes, series, currentUser } = props;

    const ncols = 4

    return (
        <div className={classes.root} id="grid-series">
            <GridList cellHeight={350} className={classes.gridList} cols={ncols}>
                <GridListTile key='Subheader' cols={ncols} style={{ height: classes.subHeader.height }}>
                    <ListSubheader component='div' className={classes.subHeader}>Series</ListSubheader>
                </GridListTile>
                {series.map(serie => (
                    <GridListTile key={serie.id}>
                        <img src={serie.img} alt={serie.name} />
                        <GridListTileBar
                            title={serie.name}
                            subtitle={<span>by: {serie.author}. Score: {serie.score}</span>}
                            actionIcon={
                                <SeriePopover serie={serie} isOwner={currentUser===serie.author}/>
                            }>
                        </GridListTileBar>
                    </GridListTile>
                ))}
            </GridList>
        </div>
    )
}

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper
    },
    gridList: {
        width: 'auto',
        height: 'auto'
    },
    subHeader: {
        background: 'rgba(0,0,0,0.75)',
        color: 'white',
        height: '45px',
    },
})

/* YE old render

return (
        <div className={classes.root} id="grid-series">
            <GridList cellHeight={350} className={classes.gridList} cols={ncols}>
                <GridListTile key='Subheader' cols={ncols} style={{ height: classes.subHeader.height }}>
                    <ListSubheader component='div' className={classes.subHeader}>Series</ListSubheader>
                </GridListTile>
                {series.map(serie => (
                    <GridListTile key={serie.id}>
                        <img src={serie.img} alt={serie.name} />
                        <GridListTileBar
                            title={serie.name}
                            subtitle={<span>by: {serie.author}. Score: {serie.score}</span>}
                            actionIcon={
                                <IconButton action={
                                    <CustomPopOver serie={serie} isOwner={serie.author === currentUser} />
                                } className={classes.icon}>
                                    <MoreVertRounded />
                                </IconButton>
                            }>
                        </GridListTileBar>
                    </GridListTile>
                ))}
            </GridList>
        </div>
    )
*/

export default withStyles(styles)(GridSeries);