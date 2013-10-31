{:config {:order 0, :description "Main", :name :bashboard}
 :data
 [
  [:node-create [] :map]
  [:node-create [:main] :map]
  [:node-create [:main :counter] :map]
  [:transform-enable [:main :counter] :inc [{:io.pedestal.app.messages/topic [:counter]}]]
  [:node-create [:main :sites] :map]
  [:transform-enable [:main :sites] :empty [{:io.pedestal.app.messages/topic [:sites "zadev1" :bookings]}]]
  [:node-create [:main :sites "zadev1"] :map]
  [:value [:main :sites "zadev1"] nil {:bookings {3296235296 {:id 3296235296, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 6495269364 {:id 6495269364, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  [:node-create [:main :sites-count] :map]
  [:value [:main :sites-count] nil 1]
  [:node-create [:main :free-sites] :map]
  [:value [:main :free-sites] nil ()]
 ]}