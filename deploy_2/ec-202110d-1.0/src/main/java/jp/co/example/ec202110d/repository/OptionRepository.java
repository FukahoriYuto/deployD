package jp.co.example.ec202110d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.Option;

/**
 * Optionsテーブルからデータを取得する
 * 
 * @author ootomokenji
 *
 */
@Repository
public class OptionRepository {

	private static final RowMapper<Option> OPTION_ROW_MAPPER = new BeanPropertyRowMapper<>(Option.class);

	@Autowired
	NamedParameterJdbcTemplate template;

	/**
	 * @return Optionが全部入ったリストを返す
	 */
	public List<Option> findAllOption() {

		String sql = "SELECT id,name,price FROM options ORDER BY id;";

		List<Option> list = template.query(sql, OPTION_ROW_MAPPER);

		return list;
	}

	/**
	 * idからOptionを取得
	 * 
	 * @param id
	 * @return
	 */
	public Option findOptionById(Integer id) {

		String sql = "SELECT id , name , price " + " FROM options " + " WHERE id = :id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Option option = template.queryForObject(sql, param, OPTION_ROW_MAPPER);
		return option;
	}
}
