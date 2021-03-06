import React from 'react';

import { withStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import ListSubheader from '@material-ui/core/ListSubheader';
import SeriePopover from './SeriePopover'
import { isAllowed } from '../utils/validator'

//To solve the cols for width
import toRenderProps from 'recompose/toRenderProps';
import withWidth , {isWidthUp} from '@material-ui/core/withWidth';
const WithWidth = toRenderProps(withWidth());


function GridSeries(props) {
    const { classes, series, currentUser, title } = props;
    
    return (
        <WithWidth> 
        {({ width }) => {
            let ncols = 4;            
            if (isWidthUp('xl', width)){
                ncols = 6;
            } else if (isWidthUp('lg', width)) {
                ncols = 4;
            } else if (isWidthUp('md', width)) {
                ncols = 3;
            } else if (isWidthUp('sm', width)) {
                ncols = 2;
            }else if (isWidthUp('xs', width)){
                ncols = 1;
            } else {
                ncols = 4;
            }

            return (
            <div className={classes.root} id="grid-series">
            <GridList cellHeight={350} className={classes.gridList} cols={ncols}>
                <GridListTile key='Subheader' cols={ncols} style={{ height: classes.subHeader.height }}>
                    <ListSubheader component='div' className={classes.subHeader}>{title}</ListSubheader>
                </GridListTile>
                {series.map(serie => (
                    <GridListTile key={serie.id}>
                        <img src={serie.picture} alt={serie.title} />
                        <GridListTileBar
                            title={serie.title}
                            subtitle={<span>by: {serie.author.fullname}. Score: {serie.score}. Views: {serie.views}</span>}
                            actionIcon={
                                <SeriePopover serie={serie} loadSeries={props.loadSeries} isOwner={isAllowed(currentUser, serie.author.email)}/>
                            }>
                        </GridListTileBar>
                    </GridListTile>
                ))}
            </GridList>
        </div>
        )}}
        
        </WithWidth>    
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