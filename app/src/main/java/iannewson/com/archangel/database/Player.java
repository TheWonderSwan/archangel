package iannewson.com.archangel.database;

import java.util.List;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Player extends RealmObject {

    public Player() {
    }

    public Player(iannewson.com.archangel.models.dtos.Player playerDto) {
        copyFrom(playerDto);
    }

    public void copyFrom(iannewson.com.archangel.models.dtos.Player playerDto) {
        if (null ==  id)
            id = playerDto._id.toString();
        matches = playerDto.matches;
        kills = playerDto.kills;
        deaths = playerDto.deaths;
        wins = playerDto.wins;
        losses = playerDto.losses;
        name = playerDto.player;
        kdr = playerDto.kdr;
        score = playerDto.score;

        if (null != playerDto.player_ids) {
            List<PlayerId> existingIds = this.getUniqueIds();
            for (String id : playerDto.player_ids) {
                boolean exists = false;
                if (null != existingIds) {
                    for (PlayerId existingId : existingIds) {
                        if (existingId.getId() == id) {
                            exists = true;
                            break;
                        }
                    }
                }
                if (!exists) {
                    this.uniqueIds.add(new PlayerId(id));
                }
            }
        }
    }

    @PrimaryKey
    public String id;
    public int matches;
    public int kills, deaths, wins, losses;
    public String name;
    public double kdr;
    public double score;
    public RealmList<PlayerId> uniqueIds;

    public boolean equals(iannewson.com.archangel.models.dtos.Player dto) {
        Player other = new Player(dto);
        return equals((Object)other);
    }

    public String getDescription() {
        return String.format("%s (kdr %.1f)", this.name, this.kdr);
    }

    public String getName() {
        return name;
    }

    public double getKdr() {
        return kdr;
    }

    // Win/loss ratio
    public double getWlr() {
        if (0 == losses) return wins;
        return ((double)wins)/((double)losses);
    }

    public List<PlayerId> getUniqueIds() {
        if (null == uniqueIds) {
            uniqueIds = new RealmList<>();
        }
        return uniqueIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return matches == player.matches &&
                kills == player.kills &&
                deaths == player.deaths &&
                wins == player.wins &&
                losses == player.losses &&
                Double.compare(player.kdr, kdr) == 0 &&
                Double.compare(player.score, score) == 0 &&
                Objects.equals(id, player.id) &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, matches, kills, deaths, wins, losses, name, kdr, score);
    }

    public double getScore() {
        return score;
    }
}
