package com.mocadev.datajpa.repository;

import com.mocadev.datajpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-05-06
 **/
public interface ItemRepository extends JpaRepository<Item, Long> {

}
